import {
  AfterViewInit,
  Component,
  ComponentFactoryResolver,
  ComponentRef,
  Injector,
  OnDestroy,
  OnInit,
  ViewChild
} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {MainPageDirective} from "./content.directive";
import {of, Subscription} from "rxjs";
import {PortofinoService} from "./portofino.service";
import {AuthenticationService} from "./security/authentication.service";
import {Page, PageChild, PageService} from "./page";
import {PageFactoryComponent} from "./page.factory";
import {NotificationService} from "./notifications/notification.service";
import {TranslateService} from "@ngx-translate/core";
import {catchError, map, mergeMap} from "rxjs/operators";

@Component({
  selector: 'portofino-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent implements AfterViewInit, OnInit, OnDestroy {
  @ViewChild(MainPageDirective, { static: false })
  contentHost: MainPageDirective;
  protected pageFactory: PageFactoryComponent;

  protected readonly subscriptions: Subscription[] = [];

  constructor(public pageService: PageService,
              protected route: ActivatedRoute, protected http: HttpClient, protected router: Router,
              protected componentFactoryResolver: ComponentFactoryResolver, injector: Injector,
              public portofino: PortofinoService, protected authenticationService: AuthenticationService,
              protected notificationService: NotificationService, protected translate: TranslateService) {
    this.pageFactory = new PageFactoryComponent(
      portofino, http, router, route, authenticationService, notificationService, translate, componentFactoryResolver,
      injector, null);
  }

  ngOnInit() {
    this.subscriptions.push(this.authenticationService.logins.subscribe(() => this.reloadPage()));
    this.subscriptions.push(this.authenticationService.logouts.subscribe(() => this.navigateToRootPage()));
    this.subscriptions.push(this.authenticationService.declinedLogins.subscribe(() => {
      //Give the page a chance to handle it
      if(!this.pageService.page || this.pageService.page.handleDeclinedLogin()) { this.navigateToRootPage(); }
    }));
  }

  protected navigateToRootPage() {
    if (this.isRootPage()) {
      window.location.reload(); //TODO
    } else {
      this.router.navigateByUrl("/");
    }
  }

  ngAfterViewInit() {
    this.subscriptions.push(this.route.url.subscribe(segments => {
      this.pageService.reset();
      this.loadAndDisplayPage(segments, this.route.snapshot.queryParams);
    }));
  }

  isRootPage() {
    const urlWithoutParams = this.router.url.substring(0, this.router.url.indexOf("?"));
    return urlWithoutParams === "" || urlWithoutParams === "/";
  }

  ngOnDestroy() {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  protected reloadPage() {
    if (!this.isRootPage()) {
      this.router.navigateByUrl(this.router.url);
    } else {
      window.location.reload(); //TODO
    }
  }

  protected loadAndDisplayPage(segments, params) {
    this.pageFactory.load(segments).subscribe(
      componentRef => {
        this.displayPage(componentRef);
        this.handleQueryParameters(componentRef.instance, params);
      },
      error => this.pageService.notifyError(error));
  }

  protected handleQueryParameters(page, params) {
    if (params.hasOwnProperty("settings")) {
      page.configure();
    } else if (!page.parent) {
      if (params.hasOwnProperty('resetPassword')) {
        this.authenticationService.showResetPasswordDialog(params.token);
      } else if (params.hasOwnProperty('confirmSignup')) {
        this.authenticationService.confirmSignup(params.token).subscribe(
          () => this.notificationService.info(this.translate.get("User successfully created."))
        );
      }
    }
  }

  protected displayPage(componentRef: ComponentRef<Page>) {
    const page = componentRef.instance;
    page.initialize();
    let viewContainerRef = this.contentHost.viewContainerRef;
    viewContainerRef.clear(); //Remove main component;
    viewContainerRef.insert(componentRef.hostView);
    this.pageService.notifyPageLoaded(page);
    page.children.forEach(child => {
      this.checkAccessibility(page, child);
    });
    const parent = page.parent;
    if (parent) {
      parent.children.forEach(child => {
        if (`${parent.path}/${child.path}` != page.path) {
          this.checkAccessibility(parent, child);
        }
      });
    }
  }

  checkAccessibility(page: Page, child: PageChild) {
    page.loadChildConfiguration(child).pipe(mergeMap(config => {
      const componentType = PageFactoryComponent.components[config.type];
      let computeSecurityCheckUrl = Page.defaultComputeSecurityCheckUrl;
      if(componentType && componentType.computeSecurityCheckUrl) {
        computeSecurityCheckUrl = componentType.computeSecurityCheckUrl;
      }
      const dummy = new DummyPage(
        this.portofino, this.http, this.router, this.route, this.authenticationService, this.notificationService, this.translate,
        computeSecurityCheckUrl(this.portofino.apiRoot, page, config.source));
      dummy.parent = page;
      dummy.configuration = config;
      return dummy.checkAccess(false).pipe(map(() => true), catchError(() => of(false)));
    })).subscribe(flag => child.accessible = flag);
  }

}

class DummyPage extends Page {

  constructor(
    portofino: PortofinoService, http: HttpClient, router: Router, route: ActivatedRoute,
    authenticationService: AuthenticationService, notificationService: NotificationService, translate: TranslateService,
    public securityCheckUrl: string) {
    super(portofino, http, router, route, authenticationService, notificationService, translate);
  }

  protected computeSecurityCheckUrl(): any {
    return this.securityCheckUrl;
  }
}

