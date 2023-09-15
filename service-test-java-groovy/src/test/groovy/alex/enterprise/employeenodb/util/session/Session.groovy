package alex.enterprise.employeenodb.util.session

interface Session {

    void put(SessionKey key, Object object);

    <T> T get(SessionKey key, Class<T> asType);

    void clear();
}