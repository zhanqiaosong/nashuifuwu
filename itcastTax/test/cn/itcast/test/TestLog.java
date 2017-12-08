package cn.itcast.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class TestLog {
    @Test
	public void testLog() throws Exception {
	 Log log = LogFactory.getLog(getClass());
	 log.debug("debug级别日志");
	 log.info("info级别日志");
	 log.warn("warn级别日志");
	 log.error("error级别日志");
	 log.fatal("fatal级别日志");
    }
}
