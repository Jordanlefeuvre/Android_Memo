package com.example.memo.ws;

import java.util.List;

public class RetourWS {

    public String url;
    public String origin;
    public form form;
    public header headers;

    public class form {

        public String memo;
    }

    public class header {

        public String Host;
    }
}
