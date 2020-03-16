package com.test.prototype.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.test.prototype.web.rest.TestUtil;

public class GIDIdentityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GIDIdentity.class);
        GIDIdentity gIDIdentity1 = new GIDIdentity();
        gIDIdentity1.setId(1L);
        GIDIdentity gIDIdentity2 = new GIDIdentity();
        gIDIdentity2.setId(gIDIdentity1.getId());
        assertThat(gIDIdentity1).isEqualTo(gIDIdentity2);
        gIDIdentity2.setId(2L);
        assertThat(gIDIdentity1).isNotEqualTo(gIDIdentity2);
        gIDIdentity1.setId(null);
        assertThat(gIDIdentity1).isNotEqualTo(gIDIdentity2);
    }
}
