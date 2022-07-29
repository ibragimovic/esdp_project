package com.esdp.demo_esdp.service;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@AllArgsConstructor
public class PropertiesService {
    private final SpringDataWebProperties pageableDefaultProps;

    public int getDefaultPageSize() {
        return pageableDefaultProps.getPageable().getDefaultPageSize();
    }


    public <T> void fillPaginationDataModel(Page<T> list,String name, int pageSize, Model model, String baseUri) {
        if (list.hasNext()) {
            model.addAttribute("nextPageLink", constructPageUri(baseUri, list.nextPageable().getPageNumber(), list.nextPageable().getPageSize()));
        }

        if (list.hasPrevious()) {
            model.addAttribute("prevPageLink", constructPageUri(baseUri, list.previousPageable().getPageNumber(), list.previousPageable().getPageSize()));
        }

        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());
        model.addAttribute(name, list.getContent());
        model.addAttribute("defaultPageSize", pageSize);
    }

    private static String constructPageUri(String uri, int page, int size) {
        return String.format("%s?page=%s&size=%s", uri, page, size);
    }
}
