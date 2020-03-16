package com.test.prototype.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.test.prototype.web.rest.TestUtil;

public class GIDUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GIDUser.class);
        GIDUser gIDUser1 = new GIDUser();
        gIDUser1.setId(1L);
        GIDUser gIDUser2 = new GIDUser();
        gIDUser2.setId(gIDUser1.getId());
        assertThat(gIDUser1).isEqualTo(gIDUser2);
        gIDUser2.setId(2L);
        assertThat(gIDUser1).isNotEqualTo(gIDUser2);
        gIDUser1.setId(null);
        assertThat(gIDUser1).isNotEqualTo(gIDUser2);
    }
}
