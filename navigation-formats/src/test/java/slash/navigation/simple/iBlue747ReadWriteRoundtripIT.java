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

import slash.navigation.base.ParserResult;
import slash.navigation.base.ReadWriteBase;
import slash.navigation.base.SimpleRoute;
import slash.navigation.base.Wgs84Position;

import java.io.IOException;

import static slash.common.io.Transfer.roundFraction;

public class iBlue747ReadWriteRoundtripIT extends ReadWriteBase {

    public void testRoundtrip() throws IOException {
        readWriteRoundtrip(TEST_PATH + "from-iblue747.csv", new TestCallback() {
            public void test(ParserResult source, ParserResult target) {
                SimpleRoute sourceRoute = (SimpleRoute) source.getAllRoutes().get(0);
                SimpleRoute targetRoute = (SimpleRoute) target.getAllRoutes().get(0);
                for (int i = 0; i < sourceRoute.getPositionCount(); i++) {
                    Wgs84Position sourcePosition = (Wgs84Position) sourceRoute.getPosition(i);
                    Wgs84Position targetPosition = (Wgs84Position) targetRoute.getPosition(i);
                    assertNearBy(roundFraction(targetPosition.getElevation(), 1), roundFraction(sourcePosition.getElevation(), 1), 1.0);
                    assertNearBy(roundFraction(targetPosition.getSpeed(), 1), roundFraction(sourcePosition.getSpeed(), 1), 1.0);
                    assertNearBy(roundFraction(targetPosition.getHeading(), 1), roundFraction(sourcePosition.getHeading(), 1));
                }
            }
        });
    }
}