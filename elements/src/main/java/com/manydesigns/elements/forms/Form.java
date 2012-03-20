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

package com.manydesigns.elements.forms;

import com.manydesigns.elements.Mode;
import com.manydesigns.elements.composites.AbstractCompositeElement;
import com.manydesigns.elements.fields.Field;
import com.manydesigns.elements.reflection.PropertyAccessor;
import com.manydesigns.elements.xml.XhtmlBuffer;
import org.jetbrains.annotations.NotNull;

/*
* @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
* @author Angelo Lupo          - angelo.lupo@manydesigns.com
* @author Giampiero Granatella - giampiero.granatella@manydesigns.com
* @author Alessio Stalla       - alessio.stalla@manydesigns.com
*/
public class Form extends AbstractCompositeElement<FieldSet> {
    public static final String copyright =
            "Copyright (c) 2005-2012, ManyDesigns srl";

    protected final Mode mode;

    public Form(Mode mode) {
        this.mode = mode;
    }

    public void toXhtml(@NotNull XhtmlBuffer xb) {
        for (FieldSet current : this) {
            current.toXhtml(xb);
        }
    }

    public boolean isRequiredFieldsPresent() {
        for (FieldSet current : this) {
            if (current.isRequiredFieldsPresent()) {
                return true;
            }
        }
        return false;
    }

    public boolean isMultipartRequest() {
        for (FieldSet current : this) {
            if (current.isMultipartRequest()) {
                return true;
            }
        }
        return false;
    }

    public Mode getMode() {
        return mode;
    }

    public Field findFieldByPropertyName(String propertyName) {
        for (FieldSet fieldSet : this) {
            for (Field field : fieldSet) {
                PropertyAccessor accessor = field.getPropertyAccessor();
                if (accessor.getName().equals(propertyName)) {
                    return field;
                }
            }
        }
        return null;
    }
}
