package com.test.prototype.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.test.prototype.web.rest.TestUtil;

public class GIDMonikerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GIDMoniker.class);
        GIDMoniker gIDMoniker1 = new GIDMoniker();
        gIDMoniker1.setId(1L);
        GIDMoniker gIDMoniker2 = new GIDMoniker();
        gIDMoniker2.setId(gIDMoniker1.getId());
        assertThat(gIDMoniker1).isEqualTo(gIDMoniker2);
        gIDMoniker2.setId(2L);
        assertThat(gIDMoniker1).isNotEqualTo(gIDMoniker2);
        gIDMoniker1.setId(null);
        assertThat(gIDMoniker1).isNotEqualTo(gIDMoniker2);
    }
}
