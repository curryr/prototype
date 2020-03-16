package com.test.prototype.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.test.prototype.web.rest.TestUtil;

public class GIDMembershipTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GIDMembership.class);
        GIDMembership gIDMembership1 = new GIDMembership();
        gIDMembership1.setId(1L);
        GIDMembership gIDMembership2 = new GIDMembership();
        gIDMembership2.setId(gIDMembership1.getId());
        assertThat(gIDMembership1).isEqualTo(gIDMembership2);
        gIDMembership2.setId(2L);
        assertThat(gIDMembership1).isNotEqualTo(gIDMembership2);
        gIDMembership1.setId(null);
        assertThat(gIDMembership1).isNotEqualTo(gIDMembership2);
    }
}
