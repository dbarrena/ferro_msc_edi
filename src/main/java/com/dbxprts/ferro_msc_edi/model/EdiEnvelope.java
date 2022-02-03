package com.dbxprts.ferro_msc_edi.model;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "D_EDI_ENVELOPES")
@DynamicUpdate
public class EdiEnvelope {
    @Id
    @Column(name = "ID_EDI_ENVELOPE")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private BigInteger ediEnvelopeID;

    @Column(name = "ID_NAVIERA")
    private BigInteger navieraID;

    @Column(name = "ENVELOPE_CONTENT")
    @Lob
    private String envelopeContent;

    @Column(name = "PROCESADO")
    private String procesado;

    @Column(name = "FECHA_PROCESADO")
    private Timestamp fechaProcesado;

    public BigInteger getEdiEnvelopeID() {
        return ediEnvelopeID;
    }

    public void setEdiEnvelopeID(BigInteger ediEnvelopeID) {
        this.ediEnvelopeID = ediEnvelopeID;
    }

    public BigInteger getNavieraID() {
        return navieraID;
    }

    public void setNavieraID(BigInteger navieraID) {
        this.navieraID = navieraID;
    }

    public String getEnvelopeContent() {
        return envelopeContent;
    }

    public void setEnvelopeContent(String envelopeContent) {
        this.envelopeContent = envelopeContent;
    }

    public String getProcesado() {
        return procesado;
    }

    public void setProcesado(String procesado) {
        this.procesado = procesado;
    }

    public Timestamp getFechaProcesado() {
        return fechaProcesado;
    }

    public void setFechaProcesado(Timestamp fechaProcesado) {
        this.fechaProcesado = fechaProcesado;
    }

    @Override
    public String toString() {
        return "EdiEnvelope{" +
                "ediEnvelopeID=" + ediEnvelopeID +
                ", navieraID=" + navieraID +
                ", procesado='" + procesado + '\'' +
                ", fechaProcesado=" + fechaProcesado +
                '}';
    }
}
