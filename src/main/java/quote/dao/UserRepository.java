package quote.dao;

import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface UserRepository extends Neo4jRepository<User, Long>{
}
