package com.test.prototype.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.test.prototype.web.rest.TestUtil;

public class GIDMonikerSetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GIDMonikerSet.class);
        GIDMonikerSet gIDMonikerSet1 = new GIDMonikerSet();
        gIDMonikerSet1.setId(1L);
        GIDMonikerSet gIDMonikerSet2 = new GIDMonikerSet();
        gIDMonikerSet2.setId(gIDMonikerSet1.getId());
        assertThat(gIDMonikerSet1).isEqualTo(gIDMonikerSet2);
        gIDMonikerSet2.setId(2L);
        assertThat(gIDMonikerSet1).isNotEqualTo(gIDMonikerSet2);
        gIDMonikerSet1.setId(null);
        assertThat(gIDMonikerSet1).isNotEqualTo(gIDMonikerSet2);
    }
}
