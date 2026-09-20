package eu.happycoders.streams;

import java.util.List;

public record Author(String name, List<Book> books) {}
