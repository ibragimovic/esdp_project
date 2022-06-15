package com.esdp.demo_esdp.util;

import java.io.IOException;
import java.io.InputStream;

public interface FileStorage {
    String save(InputStream inputStream,String fileOriginalName) throws IOException;

    void rewrite(InputStream inputStream, String identificator) throws IOException;

    void delete(String identificator) throws IOException;
}
