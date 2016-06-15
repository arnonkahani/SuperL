package com.Common.DB;

/**
 * occurs when trying to update/delete an object which is referenced
 * by another object with `ON UPDATE/DELETE RESTRICT`
 */
public class UsedException extends RuntimeException {
}
