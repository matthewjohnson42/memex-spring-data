package com.matthewjohnson42.memex.data.repository.mongo;

import com.matthewjohnson42.memex.data.entity.mongo.RawTextMongo;
import com.matthewjohnson42.memex.data.repository.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Data access object for the Mongo collection corresponding to the RawText entity described by RawTextMongo
 */
public interface RawTextMongoRepo extends MongoRepository<RawTextMongo, String>, Repository<RawTextMongo, String> {
}
