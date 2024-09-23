package com.petinder.pet_service.repository;

import com.petinder.pet_service.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface PetRepository extends JpaRepository<Pet, UUID> {
    @Query(
            value = """
                    SELECT * FROM Pet p
                    WHERE p.create_at > (
                        SELECT p2.create_at FROM Pet p2
                        WHERE p2.id = :id
                    )
                    ORDER BY p.create_at
                    LIMIT :limit
                    """,
            nativeQuery = true
    )
    Stream<Pet> findTopByCreateAtAfter(@Param("id") UUID petId, @Param("limit") int limit);

    @Query(
            value = """
                    SELECT * FROM Pet p
                    ORDER BY p.create_at
                    LIMIT :limit
                    """,
            nativeQuery = true
    )
    Stream<Pet> findTopByCreateAt(@Param("limit") int limit);

    @Query(
            value = """
                    SELECT *, ts_rank(text_search_vector, to_tsquery('english', COALESCE(:keyword, '%'))) AS rank
                    FROM pet
                    WHERE text_search_vector @@ to_tsquery('english', COALESCE(:keyword, '%'))
                    ORDER BY rank DESC
                    LIMIT :limit;
                    """,
            nativeQuery = true
    )
    Stream<Pet> searchByKeyword(@Param("keyword") String keyword, @Param("limit") long limit);
}
