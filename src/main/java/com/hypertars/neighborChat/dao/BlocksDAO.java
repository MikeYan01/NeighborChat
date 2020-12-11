package com.hypertars.neighborChat.dao;

import com.hypertars.neighborChat.model.Blocks;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlocksDAO {

    /**
     * Display all blocks
     * @return Blocks blocks model
     */
    List<Blocks> getAllBlocks();

    /**
     * Select block by bid
     * @param bid block id
     * @return Blocks blocks model
     */
    Blocks getBlockByBid (@Param("bid") int bid);

    /**
     * Select block by hid
     * @param hid hood id
     * @return List<Blocks> block models
     */
    List<Blocks> getBlocksByHid(@Param("hid") int hid);
}
