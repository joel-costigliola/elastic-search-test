package es.example.test.integration;

import static org.assertj.core.api.BDDAssertions.then;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.test.ESIntegTestCase;
import org.elasticsearch.test.ESIntegTestCase.ClusterScope;
import org.elasticsearch.test.ESIntegTestCase.Scope;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.carrotsearch.randomizedtesting.annotations.ThreadLeakScope;

@ClusterScope(scope = Scope.SUITE)
@ThreadLeakScope(ThreadLeakScope.Scope.NONE)
@RunWith(com.carrotsearch.randomizedtesting.RandomizedRunner.class)
public class EsIntegrationTest extends ESIntegTestCase {

	private static final String TRACE_ID_FIELD = "traceID";

	private static final String TRACE_TYPE = "trace";

	private static final String OPEN_TRACE_INDEX = "traces";

	private static final String OPERATION_NAME_FIELD = "operationName";

	private Client client;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.client = client();
	}

	// can't use AssertJ assertThat as ESIntegTestCase inherits from junit Assert which defines its own assertThat :(
	// but we can use BDDAssertions.then :)

	@Test
	public void search_open_tracing_traces_by_operationName() throws Exception {
		// GIVEN
		final String operationName = randomOperationName();
		indexOpenTraceDocument(randomTraceID(), operationName);
		indexOpenTraceDocument(randomTraceID(), operationName);

		// WHEN
		final SearchResponse response = searchOpenTracesByOperationName(operationName);

		// THEN
		then(response.getHits())
				.hasSize(2)
				.extracting(SearchHit::getSourceAsMap)
				.allSatisfy(hit -> then(hit).containsEntry(OPERATION_NAME_FIELD, operationName));
	}

	@Test
	public void search_open_tracing_traces_by_traceID() throws Exception {
		// GIVEN
		final String traceID = randomTraceID();
		indexOpenTraceDocument(traceID, randomOperationName());
		indexOpenTraceDocument(traceID, randomOperationName());

		// WHEN
		final SearchResponse response = searchOpenTracesByTraceID(traceID);

		// THEN
		then(response.getHits())
				.hasSize(2)
				.extracting(SearchHit::getSourceAsMap)
				.allSatisfy(hit -> then(hit).containsEntry(TRACE_ID_FIELD, traceID));
	}

	private String randomTraceID() {
		return randomAlphaOfLengthBetween(1, 50);
	}

	private String randomOperationName() {
		return randomAlphaOfLengthBetween(1, 50);
	}

	private SearchResponse searchOpenTracesByOperationName(final String operationName) {
		return this.client.prepareSearch(OPEN_TRACE_INDEX)
				.setTypes(TRACE_TYPE)
				.setQuery(QueryBuilders.commonTermsQuery(OPERATION_NAME_FIELD, operationName))
				.get();
	}

	private SearchResponse searchOpenTracesByTraceID(final String traceID) {
		return this.client.prepareSearch(OPEN_TRACE_INDEX)
				.setTypes(TRACE_TYPE)
				.setQuery(QueryBuilders.commonTermsQuery(TRACE_ID_FIELD, traceID))
				.get();
	}

	private void indexOpenTraceDocument(final String traceID, final String operationName) throws Exception {
		this.client.prepareIndex(OPEN_TRACE_INDEX, TRACE_TYPE)
				.setSource(generateOpenTrace(traceID, operationName), XContentType.JSON)
				.execute()
				.get();
		// refreshes the index otherwise we would not find anything
		refresh();
	}

	private static String generateOpenTrace(final String traceID, final String operationName) {
		return "{\n" +
				"   \"traceID\": \"" + traceID + "\",\n" +
				"   \"spanID\": \"3b1237777ef2d83\",\n" +
				"   \"parentSpanID\": \"bbe20e919b94f710\",\n" +
				"   \"operationName\": \"" + operationName + "\",\n" +
				"   \"references\": [],\n" +
				"   \"startTime\": 1510878645507000,\n" +
				"   \"duration\": 129000,\n" +
				"   \"tags\": [\n" +
				"     {\n" +
				"       \"key\": \"mvc.controller.class\",\n" +
				"       \"type\": \"string\",\n" +
				"       \"value\": \"Apis\"\n" +
				"     },\n" +
				"     {\n" +
				"       \"key\": \"mvc.controller.method\",\n" +
				"       \"type\": \"string\",\n" +
				"       \"value\": \"pong\"\n" +
				"     },\n" +
				"     {\n" +
				"       \"key\": \"source\",\n" +
				"       \"type\": \"string\",\n" +
				"       \"value\": \"KevinWasPong\"\n" +
				"     },\n" +
				"     {\n" +
				"       \"key\": \"spring.instance_id\",\n" +
				"       \"type\": \"string\",\n" +
				"       \"value\": \"172.20.41.251:Service2:18081\"\n" +
				"     },\n" +
				"     {\n" +
				"       \"key\": \"span.kind\",\n" +
				"       \"type\": \"string\",\n" +
				"       \"value\": \"server\"\n" +
				"     }\n" +
				"   ],\n" +
				"   \"logs\": [],\n" +
				"   \"processID\": \"\",\n" +
				"   \"process\": {\n" +
				"     \"serviceName\": \"service2\",\n" +
				"     \"tags\": [\n" +
				"       {\n" +
				"         \"key\": \"ip\",\n" +
				"         \"type\": \"int64\",\n" +
				"         \"value\": \"-1407964677\"\n" +
				"       }\n" +
				"     ]\n" +
				"   },\n" +
				"   \"warnings\": null,\n" +
				"   \"startTimeMillis\": 1510878645507\n" +
				" }";
	}
}
