package com.bridgelabz.fundoonotes.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.config.ElasticSearchConfig;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ElasticSearchServiceImpl implements IElasticSearchService 
{
	@Autowired
	private ElasticSearchConfig elasticSearchConfig;
	
	@Autowired
	private ObjectMapper objectMapper;

	private String INDEX = "springboot";

	private String TYPE = "noteinfo";

	/**
	 * Method is used to create Note in Elastic Search
	 */
	@Override
	public String createNote(NoteInformation noteInfo) 
	{
		Map<String, Object> notemapper = objectMapper.convertValue(noteInfo, Map.class);
		IndexRequest indexrequest = new IndexRequest(INDEX, TYPE, String.valueOf(noteInfo.getNoteid())).source(notemapper);
		IndexResponse indexResponse = null;
		try {
			indexResponse = elasticSearchConfig.client().index(indexrequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("indexrequest "+indexrequest);
		log.info("indexResponse "+indexResponse);
		log.info( "name "+indexResponse.getResult().name());
		return indexResponse.getResult().name();
	}

	/**
	 * Method is used to search the Note by title
	 */
	@Override
	public List<NoteInformation> searchByTitle(String title) {
		log.info("Title "+title);
		SearchRequest searchrequest = new SearchRequest("springboot");
		SearchSourceBuilder searchsource = new SearchSourceBuilder();
		log.info("search request "+searchrequest);
		
		searchsource.query(QueryBuilders.matchQuery("title",title));
		searchrequest.source(searchsource);
		SearchResponse searchresponse = null;
		try {
			searchresponse = elasticSearchConfig.client().search(searchrequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Result "+getResult(searchresponse).toString());
		return getResult(searchresponse);
	}

	
	private List<NoteInformation> getResult(SearchResponse searchresponse) {
		SearchHit[] searchhits = searchresponse.getHits().getHits();
		List<NoteInformation> notes = new ArrayList<>();
		if (searchhits.length > 0) {
			Arrays.stream(searchhits)
					.forEach(hit -> notes.add(objectMapper.convertValue(hit.getSourceAsMap(), NoteInformation.class)));
		}
		return notes;
	}

	/**
	 * Method is used to delete the Note
	 */
	@Override
	public String deleteNote(NoteInformation noteInfo) {
		Map<String, Object> notemapper = objectMapper.convertValue(noteInfo, Map.class);
		DeleteRequest deleterequest = new DeleteRequest(INDEX, TYPE, String.valueOf(noteInfo.getNoteid()));
		DeleteResponse deleteResponse = null;
		try {
			deleteResponse = elasticSearchConfig.client().delete(deleterequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deleteResponse.getResult().name();
	}
	
	
}
