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

package com.manydesigns.elements.fields.search;

import com.manydesigns.elements.fields.BooleanSearchValue;
import com.manydesigns.elements.reflection.PropertyAccessor;
import com.manydesigns.elements.xml.XhtmlBuffer;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;

/*
* @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
* @author Angelo Lupo          - angelo.lupo@manydesigns.com
* @author Giampiero Granatella - giampiero.granatella@manydesigns.com
* @author Alessio Stalla       - alessio.stalla@manydesigns.com
*/
public class BooleanSearchField extends AbstractSearchField {
    public static final String copyright =
            "Copyright (c) 2005-2012, ManyDesigns srl";

    //**************************************************************************
    // Fields
    //**************************************************************************

    protected BooleanSearchValue value = BooleanSearchValue.ANY;
    private static final String BOOLEAN_SEARCH_FIELD_HTML_CLASS = "mde-boolean-search-field";

    //**************************************************************************
    // Costruttori
    //**************************************************************************

    public BooleanSearchField(PropertyAccessor accessor) {
        this(accessor, null);
    }

    public BooleanSearchField(PropertyAccessor accessor, String prefix) {
        super(accessor, prefix);
    }

    //**************************************************************************
    // SearchField implementation
    //**************************************************************************

    public void toSearchString(StringBuilder sb) {
        if (value != null && value != BooleanSearchValue.ANY) {
            appendToSearchString(sb, inputName, value.getStringValue());
        }
    }

    public void configureCriteria(Criteria criteria) {
        if (value == null) {
            return;
        }

        switch(value) {
            case ANY:
                break;
            case NULL:
                criteria.isNull(accessor);
                break;
            case FALSE:
                criteria.eq(accessor, Boolean.FALSE);
                break;
            case TRUE:
                criteria.eq(accessor, Boolean.TRUE);
                break;
            default:
                logger.error("Unknown BooleanSearchValue: {}", value.name());
        }
    }

    //**************************************************************************
    // Element implementation
    //**************************************************************************

    public void readFromRequest(HttpServletRequest req) {
        String stringValue = req.getParameter(inputName);
        value = BooleanSearchValue.ANY; // default
        for (BooleanSearchValue current : BooleanSearchValue.values()) {
            if (current.getStringValue().equals(stringValue)) {
                value = current;
            }
        }
    }

    public boolean validate() {
        return true;
    }

    public void toXhtml(@NotNull XhtmlBuffer xb) {
        xb.openElement("span");
        xb.addAttribute("class", BOOLEAN_SEARCH_FIELD_HTML_CLASS);
        xb.openElement("label");
        xb.addAttribute("class", ATTR_NAME_HTML_CLASS);
        xb.write(StringUtils.capitalize(label));
        xb.closeElement("label");

        for (BooleanSearchValue current : BooleanSearchValue.values()) {
            // don't print null if the attribute is required
            if (required && current == BooleanSearchValue.NULL) {
                continue;
            }
            String idStr = id + "_" + current.name();
            String stringValue = current.getStringValue();
            boolean checked = (value == current);
            xb.writeInputRadio(idStr, inputName, stringValue, checked);
            String label = getText(current.getLabelI18N());
            xb.writeLabel(label, idStr, null);
        }
        xb.closeElement("span");
    }

    //**************************************************************************
    // Getter/setter
    //**************************************************************************

    public BooleanSearchValue getValue() {
        return value;
    }

    public void setValue(BooleanSearchValue value) {
        this.value = value;
    }
}
