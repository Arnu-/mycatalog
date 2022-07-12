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
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.table.catalog.Catalog;
import org.apache.flink.table.factories.CatalogFactory;
import org.apache.flink.table.factories.FactoryUtil;

import java.util.HashSet;
import java.util.Set;

import static me.arnu.flink.catalog.factory.MyCatalogFactoryOptions.*;
import static org.apache.flink.table.factories.FactoryUtil.PROPERTY_VERSION;

/** Factory for {@link MyCatalog}. */
public class MyCatalogFactory implements CatalogFactory {

    @Override
    public String factoryIdentifier() {
        return MyCatalogFactoryOptions.IDENTIFIER;
    }

    @Override
    public Set<ConfigOption<?>> requiredOptions() {
        final Set<ConfigOption<?>> options = new HashSet<>();
        return options;
    }

    @Override
    public Set<ConfigOption<?>> optionalOptions() {
        final Set<ConfigOption<?>> options = new HashSet<>();
        options.add(USERNAME);
        options.add(PASSWORD);
        options.add(URL);
        options.add(PROPERTY_VERSION);
        return options;
    }

    @Override
    public Catalog createCatalog(Context context) {
        final FactoryUtil.CatalogFactoryHelper helper =
                FactoryUtil.createCatalogFactoryHelper(this, context);
        helper.validate();

        return new MyCatalog(
                context.getName(),
                helper.getOptions().get(URL),
                helper.getOptions().get(USERNAME),
                helper.getOptions().get(PASSWORD));
    }
}
