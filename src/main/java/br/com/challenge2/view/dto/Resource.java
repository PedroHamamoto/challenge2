package br.com.challenge2.view.dto;

/**
 * Represents a item in the responses payloads
 */
public class Resource<T> {

    private String uri;
    private T item;

    public Resource(String uri, T item) {
        this.uri = uri;
        this.item = item;
    }

    public String getUri() {
        return uri;
    }

    public T getItem() {
        return item;
    }
}
