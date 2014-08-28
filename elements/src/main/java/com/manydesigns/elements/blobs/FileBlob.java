/*
 * Copyright (C) 2005-2014 ManyDesigns srl.  All rights reserved.
 * http://www.manydesigns.com/
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package com.manydesigns.elements.blobs;

import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/*
* @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
* @author Angelo Lupo          - angelo.lupo@manydesigns.com
* @author Giampiero Granatella - giampiero.granatella@manydesigns.com
* @author Alessio Stalla       - alessio.stalla@manydesigns.com
*/
public class FileBlob extends Blob {
    public static final String copyright =
            "Copyright (c) 2005-2014, ManyDesigns srl";

    //**************************************************************************
    // Constants
    //**************************************************************************

    public final static String COMMENT  = "Blob metadata";

    
    //**************************************************************************
    // Fields
    //**************************************************************************

    protected final File metaFile;
    protected final File dataFile;

    //**************************************************************************
    // Logging
    //**************************************************************************

    public final static Logger logger = LoggerFactory.getLogger(FileBlob.class);

    //**************************************************************************
    // Constructor
    //**************************************************************************

    public FileBlob(String code, @NotNull File metaFile, @NotNull File dataFile) {
        super(code);
        this.metaFile = metaFile;
        this.dataFile = dataFile;
    }

    //**************************************************************************
    // Methods
    //**************************************************************************

    public boolean createFiles() throws IOException {
        if (dataFile.createNewFile()) {
            logger.warn("Blob data file already exists: {}",
                    metaFile.getAbsolutePath());
            return false;
        }
        if (metaFile.createNewFile()) {
            logger.warn("Blob meta file already exists: {}",
                    metaFile.getAbsolutePath());
            return false;
        }
        return true;
    }

    @Override
    protected void storeMetaProperties(Properties metaProperties) throws IOException {
        OutputStream metaStream = null;
        try {
            metaStream = new FileOutputStream(metaFile);
            metaProperties.store(metaStream, COMMENT);
        } finally {
            IOUtils.closeQuietly(metaStream);
        }
    }

    @Override
    protected Properties readMetaProperties() throws IOException {
        Properties metaProperties = new Properties();

        InputStream metaStream = null;
        try {
            metaStream = new FileInputStream(metaFile);
            metaProperties.load(metaStream);
        } finally {
            IOUtils.closeQuietly(metaStream);
        }
        return metaProperties;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(dataFile);
    }

    //**************************************************************************
    // Getter/setter
    //**************************************************************************

    public File getMetaFile() {
        return metaFile;
    }

    public File getDataFile() {
        return dataFile;
    }

}