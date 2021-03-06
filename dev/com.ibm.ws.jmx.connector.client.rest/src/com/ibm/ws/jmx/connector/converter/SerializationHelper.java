/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.jmx.connector.converter;

import com.ibm.ws.jmx.connector.datatypes.ConversionException;

public interface SerializationHelper {

    public Object readObject(Object in, int blen, byte[] binary) throws ClassNotFoundException, ConversionException;

}
