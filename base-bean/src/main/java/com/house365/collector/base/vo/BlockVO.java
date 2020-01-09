package com.house365.collector.base.vo;

import com.house365.collector.base.entity.Block;

/**
 * 小区VO
 */
public class BlockVO extends BaseVO {

    private static final long serialVersionUID = -1517792449258800674L;

    private String cityCode;

    private int platformId;

    private String blockName;

    private String blockId;

    public BlockVO(String cityCode, int platformId, String blockName, String blockId) {
        this.cityCode = cityCode;
        this.platformId = platformId;
        this.blockName = blockName;
        this.blockId = blockId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public Block buildEntity() {
        Block block = new Block(cityCode, platformId, blockName, blockId);
        return block;
    }
}
