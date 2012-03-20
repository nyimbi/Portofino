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

package com.manydesigns.portofino.pageactions;

import com.manydesigns.portofino.dispatcher.PageAction;
import com.manydesigns.portofino.pageactions.annotations.ConfigurationClass;
import com.manydesigns.portofino.pageactions.annotations.ScriptTemplate;
import com.manydesigns.portofino.pageactions.annotations.SupportsDetail;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;

/**
 * @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo          - angelo.lupo@manydesigns.com
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Alessio Stalla       - alessio.stalla@manydesigns.com
 */
public class PageActionLogic {
    public static final String copyright =
            "Copyright (c) 2005-2012, ManyDesigns srl";

    public static boolean supportsDetail(Class<?> actionClass) {
        if(!PageAction.class.isAssignableFrom(actionClass)) {
            return false;
        }
        SupportsDetail supportsDetail = actionClass.getAnnotation(SupportsDetail.class);
        if(supportsDetail != null) {
            return supportsDetail.value();
        } else {
            return supportsDetail(actionClass.getSuperclass());
        }
    }

    public static Class<?> getConfigurationClass(Class<?> actionClass) {
        if(!PageAction.class.isAssignableFrom(actionClass)) {
            return null;
        }
        ConfigurationClass configurationClass = actionClass.getAnnotation(ConfigurationClass.class);
        if(configurationClass != null) {
            return configurationClass.value();
        } else {
            return getConfigurationClass(actionClass.getSuperclass());
        }
    }

    public static String getScriptTemplate(Class<?> actionClass) {
        if(!PageAction.class.isAssignableFrom(actionClass)) {
            return null;
        }
        ScriptTemplate scriptTemplate = actionClass.getAnnotation(ScriptTemplate.class);
        if(scriptTemplate != null) {
            String templateLocation = scriptTemplate.value();
            try {
                return IOUtils.toString(actionClass.getResourceAsStream(templateLocation));
            } catch (Exception e) {
                throw new Error("Can't load script template: " + templateLocation, e);
            }
        } else {
            String template = getScriptTemplate(actionClass.getSuperclass());
            if(template != null) {
                return template;
            } else {
                try {
                    InputStream stream =
                            PageActionLogic.class.getResourceAsStream
                                    ("/com/manydesigns/portofino/pageactions/default_script_template.txt");
                    return IOUtils.toString(stream);
                } catch (Exception e) {
                    throw new Error("Can't load script template", e);
                }
            }
        }
    }

    public static String getDescriptionKey(Class<?> actionClass) {
        PageActionName annotation = actionClass.getAnnotation(PageActionName.class);
        if(annotation != null) {
            return annotation.value();
        } else {
            return actionClass.getName();
        }
    }
}
