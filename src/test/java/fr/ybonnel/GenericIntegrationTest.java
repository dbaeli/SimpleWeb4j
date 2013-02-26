/*
 * Copyright 2013- Yan Bonnel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.ybonnel;


import fr.ybonnel.util.SimpleWebTestUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

import static fr.ybonnel.SimpleWeb.setPort;
import static fr.ybonnel.SimpleWeb.start;
import static fr.ybonnel.SimpleWeb.stop;
import static junit.framework.Assert.assertEquals;

public class GenericIntegrationTest {

    private static int port;
    private static Random random = new Random();
    private static SimpleWebTestUtil testUtil;


    @BeforeClass
    public static void startServer() {
        port = random.nextInt(10000) + 10000;
        setPort(port);
        testUtil = new SimpleWebTestUtil(port);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    start();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();


    }

    @AfterClass
    public static void stopServer() {
        stop();
    }

    @Test
    public void should_serve_basic_html_file() throws Exception {
        SimpleWebTestUtil.UrlResponse response = testUtil.doMethod("GET", "/test.html");
        assertEquals(200, response.status);
        assertEquals("just a test", response.body);
    }

}