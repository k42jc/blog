package com.techeffic.blog.service.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.service.BaseService;

@Service
public class KindEditorWriteService extends BaseService  implements IDataModelService{

	@Override
	public Map<String, Object> getData(WebContext webCtx) {
		return new HashMap<String, Object>();
	}

}
