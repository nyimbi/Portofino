/*
 * Copyright (C) 2005-2012 ManyDesigns srl.  All rights reserved.
 * http://www.manydesigns.com/
 *
 * Unless you have purchased a commercial license agreement from ManyDesigns srl,
 * the following license terms apply:
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.manydesigns.elements;

import com.manydesigns.elements.blobs.BlobManager;
import com.manydesigns.elements.i18n.TextProvider;
import ognl.OgnlContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
* @author Angelo Lupo          - angelo.lupo@manydesigns.com
* @author Giampiero Granatella - giampiero.granatella@manydesigns.com
* @author Alessio Stalla       - alessio.stalla@manydesigns.com
*/
public class ElementsContext {
    public static final String copyright =
            "Copyright (c) 2005-2012, ManyDesigns srl";

    protected TextProvider textProvider;
    protected HttpServletRequest httpServletRequest;
    protected HttpServletResponse httpServletResponse;
    protected ServletContext servletContext;
    protected OgnlContext ognlContext;
    protected BlobManager blobManager;

    //**************************************************************************
    // Constructors
    //**************************************************************************
    public ElementsContext() {}

    //**************************************************************************
    // Getters/setters
    //**************************************************************************

    public TextProvider getTextProvider() {
        return textProvider;
    }

    public void setTextProvider(TextProvider textProvider) {
        this.textProvider = textProvider;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public OgnlContext getOgnlContext() {
        return ognlContext;
    }

    public void setOgnlContext(OgnlContext ognlContext) {
        this.ognlContext = ognlContext;
    }

    public BlobManager getBlobManager() {
        return blobManager;
    }

    public void setBlobManager(BlobManager blobManager) {
        this.blobManager = blobManager;
    }
}
