/*

#     __
#    /  |  ____ ___  _
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
create @ 2022/5/23
*/
package me.arnu.flink.catalog;

import me.arnu.flink.catalog.factory.MyCatalogFactoryOptions;
import me.arnu.utils.ArnuSign;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.util.CollectionUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.apache.flink.table.api.config.ExecutionConfigOptions.TABLE_EXEC_RESOURCE_DEFAULT_PARALLELISM;

public class TestCatalog {

    protected static String url;
    protected static MyCatalog catalog;

    protected static final String TEST_CATALOG_NAME = "mysql-catalog";
    protected static final String TEST_USERNAME = "flink_metastore";
    protected static final String TEST_PWD = "flink_metastore";

    private TableEnvironment tableEnv;

    @Before
    public void setup(){

        url = "jdbc:mysql://localhost:3306/flink_metastore?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";

        catalog =
                new MyCatalog(
                        TEST_CATALOG_NAME,
                        MyCatalog.DEFAULT_DATABASE,
                        url,
                        TEST_USERNAME,
                        TEST_PWD);

        this.tableEnv = TableEnvironment.create(EnvironmentSettings.inStreamingMode());
        tableEnv.getConfig()
                .getConfiguration()
                .setInteger(TABLE_EXEC_RESOURCE_DEFAULT_PARALLELISM.key(), 1);

        // use mysql-catalog
        tableEnv.registerCatalog(MyCatalogFactoryOptions.IDENTIFIER, catalog);
        tableEnv.useCatalog(MyCatalogFactoryOptions.IDENTIFIER);
    }

    @Test
    public void test() {
        ArnuSign.printSign();
        //1\. 获取上下文环境 table的环境

        //2\. 读取score.csv

        String csvFile = "D:\\code\\test\\mycatalog\\src\\test\\resources\\score.csv";
        String createTable = "CREATE TABLE IF NOT EXISTS player_data\n" +
                "( season varchar,\n" +
                "  player varchar,\n" +
                "  play_num varchar,\n" +
                "  first_court int,\n" +
                "  `time` double,\n" +
                "  assists double,\n" +
                "  steals double,\n" +
                "  blocks double,\n" +
                "  scores double\n" +
                ") WITH ( \n" +
                "    'connector' = 'filesystem',\n" +
                "    'path' = '" + csvFile + " ',\n" +
                "    'format' = 'csv'\n" +
                ")";

        tableEnv.executeSql(createTable);

        String createView= "CREATE VIEW IF NOT EXISTS test_view " +
                " (player, play_num" +
                " ,sumaabb)" +
                " COMMENT 'test view' " +
                " AS SELECT player, play_num, assists + steals as sumaabb FROM player_data";

        tableEnv.executeSql(createView);

        String createSinkTable = "CREATE TABLE IF NOT EXISTS mysql_player_from_view\n" +
                "( " +
                "  player varchar,\n" +
                "  play_num varchar,\n" +
                "  sumaabb double\n" +
                ") WITH ( \n" +
                "    'connector' = 'jdbc',\n" +
                "    'url' = 'jdbc:mysql://localhost:3306/a01_rep_db',\n" +
                "    'table-name' = 'mysql_player_from_view',\n" +
                "    'username' = 'root',\n" +
                "    'password' = '123456'\n" +
                ")";



        tableEnv.executeSql(createSinkTable);
        tableEnv.executeSql("Insert into mysql_player_from_view\n" +
                "SELECT \n" +
                        "player ,\n" +
                "  play_num ,\n" +
                        "  sumaabb \n" +
                "FROM test_view");

        List<Row> results =
                CollectionUtil.iteratorToList(
                        tableEnv.sqlQuery("select * from mysql_player_from_view")
                                .execute()
                                .collect());

        List<Row> tresults =
                CollectionUtil.iteratorToList(
                        tableEnv.sqlQuery("select * from test_view")
                                .execute()
                                .collect());

        List<Row> presults =
                CollectionUtil.iteratorToList(
                        tableEnv.sqlQuery("select * from player_data")
                                .execute()
                                .collect());
    }
}
