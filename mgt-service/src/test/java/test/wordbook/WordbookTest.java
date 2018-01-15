package test.wordbook;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;

import com.breeze.bms.mgt.mybatis.domain.WordBook;
import com.breeze.bms.mgt.service.FreeMarkerService;
import com.breeze.bms.mgt.service.WordBookItemService;
import com.breeze.bms.mgt.service.WordBookService;
import com.selfsell.mgt.MgtApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=MgtApplication.class)
public class WordbookTest {
	@Autowired
	WordBookService wordbookService;
	@Autowired
	WordBookItemService wordbookItemService;
	@Autowired
	FreeMarkerService freeMarkerService;

	@Test
	public void genWordbook() throws Exception {
		String wordbookKey = "dateUnit";
		String filePath = "D:\\test";

		
		Map<String,Object> data = new HashMap<String,Object>();
		WordBook wb = wordbookService.findByWbKey(wordbookKey);
		data.put("wb", wb);
		data.put("wbItems", wordbookItemService.findByWbId(wb.getWbId()));
		

		String result = freeMarkerService.processTemplateIntoString("wordbookEnum.ftl", data);

		File wordbookFile = new File(filePath, "WB"+wb.getWbKey() + ".java");
		if (!wordbookFile.exists()) {
			wordbookFile.createNewFile();
		}

		FileCopyUtils.copy(result.getBytes("UTF-8"), wordbookFile);
	}
}
