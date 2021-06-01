# memex-data

Contains  abstract repositories, abstract models, concrete DTOs, model/DTO transformers, and abstract data services for use in memex services.

## usage

Requires inclusion of Spring profiles "enablemongorepositories" for use of memex mongo repositories (eg: via "spring.profiles.includes").
 
Requires inclusion of Spring profile "enableelasticrepositories" for use of elastic search repositories (eg: via "spring.profiles.includes").

Assumes component scan of dependent project will take place at package "com.matthewjohnson42.memex" or higher.

Assumes presence of configurations "db.mongo.host", "db.mongo.port", "db.elasticsearch.host", "db.elasticsearch.port"
