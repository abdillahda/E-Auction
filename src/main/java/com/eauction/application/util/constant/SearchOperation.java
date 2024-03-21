package com.eauction.application.util.constant;


public enum SearchOperation {
    EQUALITY, NEGATION, GREATER_THAN, LESS_THAN, GREATER_THAN_EQUAL, LESS_THAN_EQUAL, LIKE, STARTS_WITH, ENDS_WITH, CONTAINS, BETWEEN, INLIST, NOTINLIST;

    public static SearchOperation getSimpleOperation(final String input) {
        return switch (input) {
            case "eq" -> EQUALITY;
            case "not" -> NEGATION;
            case "gt" -> GREATER_THAN;
            case "lt" -> LESS_THAN;
            case "ge" -> GREATER_THAN_EQUAL;
            case "le" -> LESS_THAN_EQUAL;
            case "like" -> LIKE;
            case "st" -> STARTS_WITH;
            case "ew" -> ENDS_WITH;
            case "co" -> CONTAINS;
            case "be" -> BETWEEN;
            case "in" -> INLIST;
            case "nin" -> NOTINLIST;
            default -> null;
        };
    }
}
