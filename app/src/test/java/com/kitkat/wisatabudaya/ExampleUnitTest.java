package com.kitkat.wisatabudaya;

import com.kitkat.wisatabudaya.utils.Validator;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void email_isCorrect() throws Exception {
        assertEquals(new Validator().isEmailValid("irwan@mail.com"), true);
        assertEquals(new Validator().isEmailValid("a@a.a"), true);
        assertEquals(new Validator().isEmailValid("@.c"), false);
        assertEquals(new Validator().isEmailValid("@."), false);
        assertEquals(new Validator().isEmailValid("@co"), false);
        assertEquals(new Validator().isEmailValid(".com"), false);
        assertEquals(new Validator().isEmailValid("@mail."), false);
        assertEquals(new Validator().isEmailValid("a@mail."), false);
        assertEquals(new Validator().isEmailValid("a@.com"), false);
        assertEquals(new Validator().isEmailValid("mail."), false);
        assertEquals(new Validator().isEmailValid("@mail"), false);
        assertEquals(new Validator().isEmailValid("a%@mail"), false);
        assertEquals(new Validator().isEmailValid("a-@mail"), false);
        assertEquals(new Validator().isEmailValid("%a-@mail"), false);
    }
}