package com.travelcompass.api.adress.domain;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class Address {

    @Id
    @Column(name = "ADDRESS_CODE")
    private Integer addressCode;

    @Column(name = "SD_CODE")
    private String sidoCode;

    @Column(name = "SGG_CODE")
    private String sigunguCode;

    @Column(name = "UMD_CODE")
    private String umdCode;

    @Column(name = "RI_CODE")
    @Nullable
    private String riCode;

    public Address(Integer addressCode, String sidoCode, String sigunguCode, String umdCode, String riCode) {
        this.addressCode = addressCode;
        this.sidoCode = sidoCode;
        this.sigunguCode = sigunguCode;
        this.umdCode = umdCode;
        this.riCode = riCode;
    }
}
