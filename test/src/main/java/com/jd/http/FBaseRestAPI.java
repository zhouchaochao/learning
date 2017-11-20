package com.jd.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Title: FBaseRestAPI
 * Description: FBaseRestAPI
 * Company: <a href=www.cc.com>cc</a>
 * Date:  2017/9/5
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class FBaseRestAPI {

    private static Logger logger = LoggerFactory.getLogger(FBaseRestAPI.class);

    private String dbName;
    private String tableName;
    private int connectTimeout;
    private int socketTimeout;
    private int connectionRequestTimeout;
    private String url;//"http://192.168.108.137:8080/kvcommand"
    static ContentType contentType = ContentType.create("text/plain", "UTF-8");
    private static PoolingHttpClientConnectionManager cm;
    public synchronized static void initHttpPool(int soTimeout, int poolSize) {
        if (cm != null) {
            return;
        }
        cm = new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .build());
// Increase max total connection to 200
        cm.setMaxTotal(poolSize);
// Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(poolSize);
        SocketConfig.Builder builder = SocketConfig.custom();
        builder.setSoTimeout(soTimeout);
        builder.setTcpNoDelay(true);
        cm.setDefaultSocketConfig(builder.build());
    }
    public FBaseRestAPI(String url, String dbName, String tableName, int socketTimeout, int connectTimeout, int connectionRequestTimeout) {
        this.dbName = dbName;
        this.tableName = tableName;
        this.url = url;
        this.socketTimeout = socketTimeout;
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.connectTimeout = connectTimeout;
    }
    /**
     *
     * @param fields table's column names
     * @param rows
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(List<String> fields, List<List<String>> rows) throws Exception {
        SetRequest req = new SetRequest();
        req.setDatabasename(this.dbName);
        req.setTablename(this.tableName);
        RequestCommand command = new RequestCommand();
        command.setType("set");
        command.setField(fields);
        command.setValues(rows);
        req.setCommand(command);
        String content = JSON.toJSONString(req);
        StringEntity entity = new StringEntity(content, contentType);
        HttpPost setPost = new HttpPost(url);
        setPost.setEntity(entity);
        String respContent = executeHttp(setPost);
        Reply reply = JSON.parseObject(respContent, Reply.class);
        if (reply==null || reply.getCode() != 0) {
            throw new Exception("rquest error,code:" + reply.getCode());
        }
        return (int) reply.getRowsaffected();
    }
    /**
     * condJson example:
     * {
     * "field": { "column": "id", "value": 1 },
     * "relate": ">"
     * }
     * <p>
     * relate: >,<,= .etc
     *
     * @param condJson
     * @return
     * @throws Exception
     */
    public int delete(String condJson) throws Exception {
        DeleteRequest selectRequest = new DeleteRequest();
        /*FieldCond fieldCond = new FieldCond();
        fieldCond.setColumn("id");
        fieldCond.setValue(100);
        final Condition condition = new Condition();
        condition.setField(fieldCond);
        condition.setRelate("<");*/
        Condition condition = JSON.parseObject(condJson, Condition.class);
        Filter filter = new Filter();
        ArrayList<Condition> andList = new ArrayList<Condition>();
        andList.add(condition);
        filter.setAnd(andList);
        DeleteCommand deleteCommand = new DeleteCommand();
        deleteCommand.setType("del");
        deleteCommand.setFilter(filter);
        selectRequest.setTablename(this.tableName);
        selectRequest.setDatabasename(this.dbName);
        selectRequest.setCommand(deleteCommand);
        String content = JSON.toJSONString(selectRequest);
        StringEntity entity = new StringEntity(content, contentType);
        HttpPost setPost = new HttpPost(this.url);
        setPost.setEntity(entity);
        String respContent = executeHttp(setPost);
        Reply reply = JSON.parseObject(respContent, Reply.class);
        if (reply==null || reply.getCode() != 0) {
            throw new Exception("rquest error,code:" + reply.getCode());
        }
        return (int) reply.getRowsaffected();
    }
    /**
     * condJson example:
     * {
     * "field": { "column": "id", "value": 1 },
     * "relate": ">"
     * }
     * <p>
     * relate: >,<,= .etc
     *
     * @param selFields table's column names
     * @param condJson
     * @param offset
     * @param maxSize must less than 10000
     * @return
     * @throws Exception
     */
    public List<Object[]> select(List<String> selFields,String condJson, int offset, int maxSize) throws Exception {
        SelectRequest selectRequest = new SelectRequest();
        Limit limit = new Limit();
        limit.setOffset(offset);
        limit.setRowcount(maxSize);
            /*FieldCond fieldCond = new FieldCond();
            fieldCond.setColumn("id");
            fieldCond.setValue(100);
            final Condition condition = new Condition();
            condition.setField(fieldCond);
            condition.setRelate("<");*/
        Condition condition = JSON.parseObject(condJson, Condition.class);
        Filter filter = new Filter();
        ArrayList<Condition> andList = new ArrayList<Condition>();
        andList.add(condition);
        filter.setAnd(andList);
        filter.setLimit(limit);
        /*ArrayList<String> fields = new ArrayList<String>();
        fields.add("id");
        fields.add("v");*/
        QueryCommand queryCommand = new QueryCommand();
        queryCommand.setField(selFields);
        queryCommand.setType("get");
        queryCommand.setFilter(filter);
        //queryCommand.setLimit(limit);
        selectRequest.setTablename(this.tableName);
        selectRequest.setDatabasename(this.dbName);
        selectRequest.setCommand(queryCommand);
        String content = JSONObject.toJSONString(selectRequest);
        StringEntity entity = new StringEntity(content, contentType);
        HttpPost setPost = new HttpPost(this.url);
        setPost.setEntity(entity);
        String respContent = executeHttp(setPost);
        Reply reply = JSON.parseObject(respContent, Reply.class);
        if (reply==null || reply.getCode() != 0) {
            throw new Exception("rquest error,code:" + reply.getCode());
        }
        return reply.getValues();
    }



    /**
     * condJson example:
     * {
     * "field": { "column": "id", "value": 1 },
     * "relate": ">"
     * }
     * <p>
     * relate: >,<,= .etc
     *
     * @param selFields table's column names
     * @param condJson
     * @param offset
     * @param maxSize must less than 10000
     * @return
     * @throws Exception
     */
    public List<Object[]> select(List<String> selFields,String condJson, int offset, int maxSize,String start_key,String end_key) throws Exception {
        SelectRequest selectRequest = new SelectRequest();
        Limit limit = new Limit();
        limit.setOffset(offset);
        limit.setRowcount(maxSize);
            /*FieldCond fieldCond = new FieldCond();
            fieldCond.setColumn("id");
            fieldCond.setValue(100);
            final Condition condition = new Condition();
            condition.setField(fieldCond);
            condition.setRelate("<");*/

        Scope scope = new Scope(start_key,end_key);

        Condition condition = JSON.parseObject(condJson, Condition.class);
        Filter filter = new Filter();
        ArrayList<Condition> andList = new ArrayList<Condition>();
        andList.add(condition);
        filter.setAnd(andList);
        filter.setLimit(limit);
        filter.setScope(scope);
        /*ArrayList<String> fields = new ArrayList<String>();
        fields.add("id");
        fields.add("v");*/
        QueryCommand queryCommand = new QueryCommand();
        queryCommand.setField(selFields);
        queryCommand.setType("get");
        queryCommand.setFilter(filter);
        //queryCommand.setLimit(limit);
        selectRequest.setTablename(this.tableName);
        selectRequest.setDatabasename(this.dbName);
        selectRequest.setCommand(queryCommand);
        String content = JSONObject.toJSONString(selectRequest);
        //logger.info("请求体：" + content);
        StringEntity entity = new StringEntity(content, contentType);
        HttpPost setPost = new HttpPost(this.url);
        setPost.setEntity(entity);
        String respContent = executeHttp(setPost);
        Reply reply = JSON.parseObject(respContent, Reply.class);
        if (reply==null || reply.getCode() != 0) {
            throw new Exception("rquest error,code:" + reply.getCode());
        }
        return reply.getValues();
    }


    public String getTableinfo() throws Exception {
        HttpGet httpGet = new HttpGet(this.url.replace("kvcommand","tableinfo") + "?dbname=" + this.dbName + "&tablename=" + this.tableName);
        String respContent = executeHttp(httpGet);
        return respContent;
    }

    public List<Range> getRanges() throws Exception {
        JSONObject tableInfoJSON = JSON.parseObject(getTableinfo());
        JSONArray rangeJsonArray  = tableInfoJSON.getJSONObject("data").getJSONArray("routes");
        return rangeJsonArray.toJavaList(Range.class);
    }

    public String executeHttp(HttpRequestBase request) throws Exception {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        if (cm == null) {
            throw new RuntimeException("please call initHttpPool()");
        }
        // 根据默认超时限制初始化requestConfig
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(
                connectionRequestTimeout).setSocketTimeout(socketTimeout).setConnectTimeout(
                connectTimeout).build();
        try {
            client = HttpClients.custom()
                    .setConnectionManager(cm)
                    .setDefaultRequestConfig(requestConfig)
                    .build();
            response = client.execute(request);
            if (response.getStatusLine().getStatusCode() != 200) {
                request.abort();
                logger.info(response.toString());
                throw new Exception("http response code is " + response.getStatusLine().getStatusCode());
            }
            HttpEntity respEntity = response.getEntity();
            String content = EntityUtils.toString(respEntity);
            return content;
        } catch (Exception e) {
            request.abort();
            throw e;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException ioe) {
            }
            if (request != null) {
                request.releaseConnection();
            }
        }
    }
}

class RequestCommand {
    private String type;
    private List<String> field = new ArrayList();
    private List<List<String>> values = new ArrayList<List<String>>();
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<String> getField() {
        return field;
    }
    public void setField(List<String> field) {
        this.field = field;
    }
    public List<List<String>> getValues() {
        return values;
    }
    public void setValues(List<List<String>> values) {
        this.values = values;
    }
}

class QueryCommand {
    private String type;
    private List<String> field = new ArrayList();
    private Filter filter;
    //private Limit limit;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<String> getField() {
        return field;
    }
    public void setField(List<String> field) {
        this.field = field;
    }
    public Filter getFilter() {
        return filter;
    }
    public void setFilter(Filter filter) {
        this.filter = filter;
    }
/*    public Limit getLimit() {
        return limit;
    }
    public void setLimit(Limit limit) {
        this.limit = limit;
    }*/
}

class Filter {
    private ArrayList<Condition> and = new ArrayList<Condition>();
    private Limit limit;
    private Scope scope;
    public ArrayList<Condition> getAnd() {
        return and;
    }
    public void setAnd(ArrayList<Condition> and) {
        this.and = and;
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}

class Condition {
    private String relate;
    private FieldCond field;
    public String getRelate() {
        return relate;
    }
    public void setRelate(String relate) {
        this.relate = relate;
    }
    public FieldCond getField() {
        return field;
    }
    public void setField(FieldCond field) {
        this.field = field;
    }
}

class FieldCond {
    private String column;
    private String value;
    public String getColumn() {
        return column;
    }
    public void setColumn(String column) {
        this.column = column;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}

class Limit {
    private int offset;
    private int rowcount;
    public int getOffset() {
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
    public int getRowcount() {
        return rowcount;
    }
    public void setRowcount(int rowcount) {
        this.rowcount = rowcount;
    }
}

class Scope {
    private String start;
    private String end;

    public Scope(String start,String end){
        this.start = (start == null) ? "" : start;
        this.end = (end == null) ? "" : end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}

class SelectRequest {
    private String databasename;
    private String tablename;
    private QueryCommand command;
    public String getDatabasename() {
        return databasename;
    }
    public void setDatabasename(String databasename) {
        this.databasename = databasename;
    }
    public String getTablename() {
        return tablename;
    }
    public void setTablename(String tablename) {
        this.tablename = tablename;
    }
    public QueryCommand getCommand() {
        return command;
    }
    public void setCommand(QueryCommand command) {
        this.command = command;
    }
}

class DeleteRequest {
    private String databasename;
    private String tablename;
    private DeleteCommand command;
    public String getDatabasename() {
        return databasename;
    }
    public void setDatabasename(String databasename) {
        this.databasename = databasename;
    }
    public String getTablename() {
        return tablename;
    }
    public void setTablename(String tablename) {
        this.tablename = tablename;
    }
    public DeleteCommand getCommand() {
        return command;
    }
    public void setCommand(DeleteCommand command) {
        this.command = command;
    }
}

class DeleteCommand {
    private String type;
    private Filter filter;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Filter getFilter() {
        return filter;
    }
    public void setFilter(Filter filter) {
        this.filter = filter;
    }

}

class SetRequest {
    private String databasename;
    private String tablename;
    private RequestCommand command;
    public String getDatabasename() {
        return databasename;
    }
    public void setDatabasename(String databasename) {
        this.databasename = databasename;
    }
    public String getTablename() {
        return tablename;
    }
    public void setTablename(String tablename) {
        this.tablename = tablename;
    }
    public RequestCommand getCommand() {
        return command;
    }
    public void setCommand(RequestCommand command) {
        this.command = command;
    }
}

class Reply {
    private int code;
    private long rowsaffected;
    private List<Object[]> values;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public long getRowsaffected() {
        return rowsaffected;
    }
    public void setRowsaffected(long rowsaffected) {
        this.rowsaffected = rowsaffected;
    }
    public List<Object[]> getValues() {
        return values;
    }
    public void setValues(List<Object[]> values) {
        this.values = values;
    }
}

class Range {
    private int range_id;
    private String start_key;
    private String end_key;

    public int getRange_id() {
        return range_id;
    }

    public void setRange_id(int range_id) {
        this.range_id = range_id;
    }

    public String getStart_key() {
        return start_key;
    }

    public void setStart_key(String start_key) {
        this.start_key = start_key;
    }

    public String getEnd_key() {
        return end_key;
    }

    public void setEnd_key(String end_key) {
        this.end_key = end_key;
    }

    @Override
    public String toString(){
        return "range_id:" + range_id + " start_key:" + start_key + " end_key:" + end_key;
    }
}
