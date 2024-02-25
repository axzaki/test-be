package com.obs.testbe.controller;

import com.obs.testbe.model.Item;
import com.obs.testbe.service.ItemService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.obs.testbe.helper.Constant.API_URL;

@RestController
@RequiredArgsConstructor
public class ItemAPIController {
    private final ItemService itemService;

    @GetMapping(value = API_URL+"/item/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listing Item with pagination", notes = "Listing Item with pagination")
    public Object getItemList(@PageableDefault(size = 10, page = 0) Pageable pageable)
    {
        return itemService.getItemList(pageable);
    }

    @GetMapping(value = API_URL+"/item", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get Listing Item with name contains", notes = "get Listing Item with name contains")
    public Object getItemListByName(@RequestParam(name = "name") String name,
                              @PageableDefault(size = 10, page = 0) Pageable pageable)
    {
        return itemService.getItemListByName(name,pageable);
    }

    @GetMapping(value = API_URL+"/item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get Item by ID", notes = "get Item by ID")
    public Object getItem(@PathVariable(name = "id") String id)
    {
        return itemService.getItemById(id);
    }

    @DeleteMapping(value = API_URL+"/item/del/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "delete item by ID", notes = "delete item by ID")
    public Object deleteItem(@PathVariable(name = "id") String id)
    {
        return itemService.deleteItemById(id);
    }

    @PostMapping(value = API_URL+"/item/save", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "save item", notes = "save item")
    @ResponseStatus(HttpStatus.OK)
    public Object saveItem(@RequestBody Item item)
    {
        return itemService.saveItem(item);
    }
}
