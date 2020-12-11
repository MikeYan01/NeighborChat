package com.hypertars.neighborChat.dao;

import com.hypertars.neighborChat.model.Neighbors;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NeighborsDAO {

    /**
     * get all neighbors of the user
     * @param uid user id
     * @return List<Neighbors> neighbors models
     */
    List<Neighbors> getNeighbors(@Param("uid") int uid);

    /**
     * get all neighbors of the user from same block
     * @param uid user id
     * @return List<Neighbors> neighbors models
     */
    List<Neighbors> getNeighborsFromBlock(@Param("uid") int uid);

    /**
     * get all neighbors of the user from same hood
     * @param uid user id
     * @return List<Neighbors> neighbors models
     */
    List<Neighbors> getNeighborsFromHood(@Param("uid") int uid);

    /**
     * check whether uid A and uid B are already neighbors
     * @param neighbors neighbor
     * @return counts
     */
    int checkNeighbor(Neighbors neighbors);

    /**
     * add new neighbors
     * @param neighbors neighbor
     */
    void addNeighbor(Neighbors neighbors);

    /**
     * delete neighbor
     * @param neighbors neighbor
     */
    void deleteNeighbor(Neighbors neighbors);

    /**
     * delete all neighbors (when joining new block)
     * @param uidA uid A
     */
    void deleteAllNeighbors(@Param("uidA") int uidA);
}
