package pub.lxd.blog.tags;

import jetbrick.template.JetAnnotations;
import jetbrick.template.runtime.JetTagContext;
@JetAnnotations.Tags
public class JetxTags {
	public static void pullPageIn(JetTagContext jtCtx,String pageName){
		System.out.println(jtCtx.getEngine().getTemplate(pageName));
		System.out.println(jtCtx.getBodyContent());
		System.out.println("This method has been handler!");
	}
}
