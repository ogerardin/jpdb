/*
    JPDB, a Java library to read/write Palm OS database file formats.
    Copyright (C) 2005 Olivier Gérardin

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package oge.jpdb;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Utility class to transfer shorts, ints, Dates and Strings to and from a byte array,
 * plus other miscellaneous helper methods.
 * 
 * @author Olivier Gérardin
 */
final class Bits {
    
    private Bits() {
    }

    /**
     * Extracts a NULL-terminated string from the specified byte array (with a maximum size)
     * 
     * @param buffer the source byte array
     * @param start the index of the first character
     * @param maxSize the maximum size of the string to extract
     * @return
     */
    static String parseString(byte[] buffer, int start, int maxSize) {
        StringBuffer stringBuffer = new StringBuffer(maxSize);
        for (int i = start; i < start + maxSize; i++) {
            char c = (char) buffer[i];
            if (c == 0) {
                break;
            }
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }

    static int getInt(byte[] bb, int bi) {
        return makeInt(bb[bi + 0], bb[bi + 1], bb[bi + 2], bb[bi + 3]);
    }

    static short getShort(byte[] bb, int bi) {
        return makeShort(bb[bi + 0], bb[bi + 1]);
    }

    static int makeInt(byte b3, byte b2, byte b1, byte b0) {
        return (int) ((((b3 & 0xff) << 24) 
                | ((b2 & 0xff) << 16)
                | ((b1 & 0xff) << 8) 
                | ((b0 & 0xff) << 0)));
    }

    static short makeShort(byte b1, byte b0) {
        return (short) ((b1 << 8) 
                | (b0 & 0xff));
    }

    private static byte short1(short x) {
        return (byte) (x >> 8);
    }

    private static byte short0(short x) {
        return (byte) (x >> 0);
    }

    static void putShort(byte[] bb, int bi, short x) {
    	bb[bi + 0] = short1(x);
    	bb[bi + 1] = short0(x);
    }

    private static byte int3(int x) {
        return (byte) (x >> 24);
    }

    private static byte int2(int x) {
        return (byte) (x >> 16);
    }

    private static byte int1(int x) {
        return (byte) (x >> 8);
    }

    private static byte int0(int x) {
        return (byte) (x >> 0);
    }

    static void putInt(byte[] bb, int bi, int x) {
    	bb[bi + 0] = int3(x);
    	bb[bi + 1] = int2(x);
    	bb[bi + 2] = int1(x);
    	bb[bi + 3] = int0(x);
    }

    /**
     * Converts a date in the palm format (number of seconds since January 1, 1904)
     * into a native Java Date.
     * 
     * @param seconds
     * @return
     */
    static Date parseDate(int seconds) {
        if (seconds == 0) {
            return null;
        }
    	Calendar calendar = new GregorianCalendar(1904, 0, 1);
    	calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * Converts anative Java Date into the palm date format (numer of seconds since january 1, 1904)
     * 
     * @param date
     * @return
     */
    static int getPalmDate(Date date) {
        if (date == null) {
            return 0;
        }
        long seconds1 = date.getTime() / 1000;
    	Calendar calendar0 = new GregorianCalendar(1904, 0, 1);
    	long seconds0 = calendar0.getTimeInMillis() / 1000;
    	return (int) (seconds1 - seconds0);
    }

    /**
     * compare the properties of two given Javabeans
     * 
     * @param obj0 first object
     * @param obj1 second oobject
     * @return true if and only if 
     *  1) objects are of the same class and 
     *  2) the value of every property of the first object equals the value of 
     *  the corresponding property of the second object
     * @throws IntrospectionException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static boolean beanPropertiesEquals(Object obj0, Object obj1) 
        throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException 
    {
        if (obj0.getClass() != obj1.getClass()) {
            return false;
        }
        
        BeanInfo beanInfo = Introspector.getBeanInfo(obj0.getClass());
        PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();

        for (int i = 0; i < properties.length; i++) {
            PropertyDescriptor property = properties[i];
            Method getter = property.getReadMethod();
            if (getter == null) {
                continue;
            }
            
            Object val0 = getter.invoke(obj0, null);
            Object val1 = getter.invoke(obj1, null);
            if (val0 != null) {
                if (! val0.equals(val1)) {
                    return false;
                }
            }
            else if (val1 != null) {
                return false;
            }
        }
        
        return true;
    }

}
