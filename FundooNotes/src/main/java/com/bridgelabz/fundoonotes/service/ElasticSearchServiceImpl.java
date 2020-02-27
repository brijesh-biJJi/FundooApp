package com.bridgelabz.fundoonotes.service;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
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
}
