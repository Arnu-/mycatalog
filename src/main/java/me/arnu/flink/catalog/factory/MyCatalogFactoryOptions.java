/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.arnu.flink.catalog.factory;

import me.arnu.flink.catalog.MyCatalog;
import org.apache.flink.annotation.Internal;
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.table.catalog.CommonCatalogOptions;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * {@link ConfigOption}s for {@link MyCatalog}.
 */
@Internal
public class MyCatalogFactoryOptions {

    public static final String IDENTIFIER = "mysql_catalog";

    public static final ConfigOption<String> DEFAULT_DATABASE =
            ConfigOptions.key(CommonCatalogOptions.DEFAULT_DATABASE_KEY)
                    .stringType()
                    .noDefaultValue();

    public static final ConfigOption<String> USERNAME =
            ConfigOptions.key("mysql-catalog-username").stringType().noDefaultValue();

    public static final ConfigOption<String> PASSWORD =
            ConfigOptions.key("mysql-catalog-password").stringType().noDefaultValue();

    public static final ConfigOption<String> URL =
            ConfigOptions.key("mysql-catalog-url").stringType().noDefaultValue();

    /*static {
        ResourceBundle rb = ResourceBundle.getBundle("mysql-catalog");
        String username = rb.getString("mysql-catalog-username");
        USERNAME = ConfigOptions.key("mysql-catalog-username")
                .stringType()
                .defaultValue(username);

        String password = rb.getString("mysql-catalog-password");
        PASSWORD = ConfigOptions.key("mysql-catalog-password")
                .stringType()
                .defaultValue(password);

        String url = rb.getString("mysql-catalog-url");
        URL = ConfigOptions.key("mysql-catalog-url")
                .stringType()
                .defaultValue(url);
    }*/

    private MyCatalogFactoryOptions() {
    }
}
