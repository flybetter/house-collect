package com.house365.collector.base.entity;

import javax.persistence.*;

/**
 * 小区源表
 */
@Entity
@Table(name = "crawl_block")
public class Block extends BaseEntity {

    /**
     * 城市代码
     */
    @Column(name = "city_code", nullable = false)
    private String cityCode;

    /**
     * 平台id
     */
    @Column(name = "platform_id", nullable = false)
    private int platformId;

    /**
     * 小区名
     */
    @Column(name = "block_name", nullable = false)
    private String blockName;

    /**
     * 平台小区id
     */
    @Column(name = "block_id", nullable = false)
    private String blockId;

    /**
     * 365二手房小区名
     */
    @Column(name = "local_block_name")
    private String localBlockName;

    /**
     * 365二手房小区id
     */
    @Column(name = "local_block_id")
    private String localBlockId;

    public Block(String cityCode, int platformId, String blockName, String blockId) {
        this.cityCode = cityCode;
        this.platformId = platformId;
        this.blockName = blockName;
        this.blockId = blockId;
    }

    protected Block() {
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

    public String getLocalBlockName() {
        return localBlockName;
    }

    public void setLocalBlockName(String localBlockName) {
        this.localBlockName = localBlockName;
    }

    public String getLocalBlockId() {
        return localBlockId;
    }

    public void setLocalBlockId(String localBlockId) {
        this.localBlockId = localBlockId;
    }

    @Override
    public String toString() {
        return "Block{" +
                "cityCode='" + cityCode + '\'' +
                ", platformId=" + platformId +
                ", blockName='" + blockName + '\'' +
                ", blockId='" + blockId + '\'' +
                ", localBlockName='" + localBlockName + '\'' +
                ", localBlockId='" + localBlockId + '\'' +
                '}';
    }
}
