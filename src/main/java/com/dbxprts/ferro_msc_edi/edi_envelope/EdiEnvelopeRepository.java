package com.dbxprts.ferro_msc_edi.edi_envelope;

import com.dbxprts.ferro_msc_edi.model.EdiEnvelope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface EdiEnvelopeRepository extends JpaRepository<EdiEnvelope, BigInteger> {
    @Query(value = "SELECT ID_EDI_ENVELOPE, \n" +
            "ID_NAVIERA, \n" +
            "ENVELOPE_CONTENT,\n" +
            "NVL(PROCESADO, '0') AS PROCESADO,\n" +
            "FECHA_PROCESADO\n" +
            "FROM D_EDI_ENVELOPES\n" +
            "WHERE ( PROCESADO = '0' OR  PROCESADO IS NULL)\n" +
            "AND FECHA_CREACION > TRUNC(SYSDATE - 3)" +
            "AND ID_NAVIERA IN (1014)",
            nativeQuery = true)
    List<EdiEnvelope> getUnprocessedEnvelopes();
}


