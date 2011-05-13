/*
    This file is part of RouteConverter.

    RouteConverter is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    RouteConverter is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with RouteConverter; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Copyright (C) 2007 Christian Pesch. All Rights Reserved.
*/

package slash.navigation.simple;

import slash.navigation.base.NavigationTestCase;
import slash.navigation.base.Wgs84Position;

public class OpelNaviFormatTest extends NavigationTestCase {
    OpelNaviFormat format = new OpelNaviFormat();

    public void testIsValidLine() {
        assertTrue(format.isValidLine("8.402824,49.986889,\"Tor 45\",\"Opel, Rüsselsheim\",\"+49-6142-77-0\""));
        assertTrue(format.isValidLine(" 8.402824 , 49.986889 , \"Tor 45\" , \"Opel, Rüsselsheim\" , \"+49-6142-77-0\" "));
        assertTrue(format.isValidLine("8.402824,49.986889,\"Tor 45\",\"Opel, Rüsselsheim\",\"\""));
        assertTrue(format.isValidLine("8.402824,49.986889,\"Tor 45\",\"\",\"\""));
        // assertTrue(format.isValidLine("0.51260,51.296028,\"Village Hotel\",\"Sandling, Maidstone, Forstal Rd\""));
    }

    public void testIsPosition() {
        assertTrue(format.isPosition("8.402824,49.986889,\"Tor 45\",\"Opel, Rüsselsheim\",\"+49-6142-77-0\""));
        // assertTrue(format.isPosition("0.51260,51.296028,\"Village Hotel\",\"Sandling, Maidstone, Forstal Rd\""));
    }

    public void testParsePosition() {
        Wgs84Position position = format.parsePosition("8.402824,49.986889,\"Tor 45\",\"Opel, Rüsselsheim\",\"+49-6142-77-0\"", null);
        assertEquals(8.402824, position.getLongitude());
        assertEquals(49.986889, position.getLatitude());
        assertEquals("Tor 45;Opel, Rüsselsheim;+49-6142-77-0", position.getComment());
    }

    public void testParseNegativePosition() {
        Wgs84Position position = format.parsePosition("-8.402824,-49.986889,\"Tor 45\",\"\",\"\"", null);
        assertEquals(-8.402824, position.getLongitude());
        assertEquals(-49.986889, position.getLatitude());
        assertEquals("Tor 45", position.getComment());
    }
}