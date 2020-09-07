package com.small.qcit.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.dom4j.DocumentException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.quaero.method.QCITApi5;
import com.quest.common.util.Json;
import com.quest.software.bus4j.datatype.CmdCodeException;
import com.quest.software.bus4j.datatype.ErrCodeException;
import com.quest.software.bus4j.datatype.NodeId;
import com.quest.software.bus4j.module.DataFormatException;
import com.quest.software.bus4j.module.ExecuteException;
import com.quest.software.common.bus.NodeManager;
import com.quest.software.common.bus.NodeManager.Node;

import net.sf.json.JSONArray;

@RestController
public class motorController {
	
	 @MessageMapping("/loadMotor")
	 //@SendTo("/topic/getallMotor")
	 @SendTo("/user/allmotor/alone/getResponse")
	 public String loadMotor() {
		 System.out.println("加载所有电机--------");
		// Map<String, NodeId> nodeIdMap = new TreeMap<>();
		 List<String> nodeIdMap=new ArrayList<String>();
		 String strs="";
			int i = 0;
		 for (Node node : NodeManager.SMALLQCIT.MB_NODE_LIST) {
				String prefix = (i < 10 ? ("0" + i) : (i + "")) + "_";
				String[] nodeValue = node.getNodeName().split("_");
				//nodeIdMap.put(nodeValue[0] + "_" + prefix + nodeValue[1], node.getNodeId());
				//nodeIdMap.add(nodeValue[0] + "_" + prefix + nodeValue[1]);
				strs=strs+nodeValue[0] + "_" + prefix + nodeValue[1]+",";
				i++;
			}
		 System.out.println("aa="+strs);
		//String strs= JSONArray.fromObject(nodeIdMap).toString();
		 //String[] strs = nodeIdMap.toArray(new String[nodeIdMap.size()]);
		 
	     return strs.substring(0, strs.length()-1);
	 }

}
