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

package org.apache.ambari.view.hive20.internal.query.generators;

import com.google.common.base.Optional;
import org.apache.ambari.view.hive20.exceptions.ServiceException;
import org.apache.parquet.Strings;

public class RenameTableQueryGenerator implements QueryGenerator {
  private String oldDatabaseName;
  private final String oldTableName;
  private final String newDatabaseName;
  private final String newTableName;

  public RenameTableQueryGenerator(String oldDatabaseName, String oldTableName, String newDatabaseName, String newTableName) {
    this.oldDatabaseName = oldDatabaseName;
    this.oldTableName = oldTableName;
    this.newDatabaseName = newDatabaseName;
    this.newTableName = newTableName;
  }

  public String getOldDatabaseName() {
    return oldDatabaseName;
  }

  public String getOldTableName() {
    return oldTableName;
  }

  public String getNewDatabaseName() {
    return newDatabaseName;
  }

  public String getNewTableName() {
    return newTableName;
  }

  /**
   * ALTER TABLE table_name RENAME TO new_table_name;
   * @return Optional rename query if table has changed.
   * @throws ServiceException
   */
  @Override
  public Optional<String> getQuery() throws ServiceException {
    StringBuilder queryBuilder = new StringBuilder("ALTER TABLE `");
    if(!Strings.isNullOrEmpty(this.getOldDatabaseName())){
      queryBuilder.append(this.getOldDatabaseName().trim()).append("`.`");
    }
    if(!Strings.isNullOrEmpty(this.getOldTableName())){
      queryBuilder.append(this.getOldTableName().trim());
    }else{
      throw new ServiceException("current table name cannot be null or empty.");
    }
    queryBuilder.append("` RENAME TO `");

    if(!Strings.isNullOrEmpty(this.getNewDatabaseName())){
      queryBuilder.append(this.getNewDatabaseName().trim()).append("`.`");
    }

    if(!Strings.isNullOrEmpty(this.getNewTableName())){
      queryBuilder.append(this.getNewTableName().trim());
    }else{
      throw new ServiceException("new table name cannot be null or empty.");
    }

    queryBuilder.append("`");
    return Optional.of(queryBuilder.toString());
  }
}
