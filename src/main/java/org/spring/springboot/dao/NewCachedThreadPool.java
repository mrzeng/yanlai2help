package org.spring.springboot.dao;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.spring.springboot.zw.util.DateUtil.getAfterDayDate;

@Component
@Configurable
@EnableScheduling

public class NewCachedThreadPool {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    private final int AAA = 11;
    public static int i = 0;

    public static void main(String[] args) {
        String aaa = "353601eea8764e61bc94eac174b7b9f4";
//        String qryTm = aaa.substring(0,4)+"-"+ aaa.substring(4,6)+"-"+ aaa.substring(6,8);
//        aaa=encryptBasedDes(aaa);
//        System.out.print(aaa);

        long begintime = System.nanoTime();
        // 创建一个线程池，用10个线程处理
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int index = 0; index < 100; index++) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        new Thread().sleep(100);  //模拟耗时操作
                        System.out.println("[1]" + Thread.currentThread().getName());
                    } catch (Exception e) {
                    }
                }
            };
           System.out.println(index);
            pool.execute(run);
            long endtime = System.nanoTime();
            long costTime = (endtime - begintime)/1000;
            System.out.println("[1] done!===="+costTime);
        }

//        pool.shutdown();

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date today = new Date();
//        String endDate = sdf.format(today);//当前日期
//        //获取三十天前日期
//        Calendar theCa = Calendar.getInstance();
//        theCa.setTime(today);
//        //最后一个数字scoreEffectTime可改，代表在当前日期基础上扣减指定的天数
//        theCa.add(theCa.DATE, -1);
//        Date start = theCa.getTime();
//        String startDate = sdf.format(start);//三十天之前日期
//        System.out.print(startDate);
//        String id = null;
//        int idd = 1;
//        while (idd < 900000) {
//            id = String.valueOf(idd);
//            PerIntegrationBasis perIntegrationBasis = new PerIntegrationBasis();
//            perIntegrationBasis.setUpdateTm(DateUtil.getTime());
//            perIntegrationBasis.setLastTimeScore(DateUtil.getTime());
//            perIntegrationBasis.setTmSmp(DateUtil.getTime());
//            perIntegrationBasis.setId(id);
//            perIntegrationBasis.setYesterdayScore("11");
//            perIntegrationBasis.setScore("999");
//            DaoSupport dao = new  DaoSupport();
//            try {
//                System.out.println("okkkkkkkkkkkkk");
//                dao.update("PerIntegrationBasisMapper.insert", perIntegrationBasis);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println("更新个人积分汇总信息表");
//
//            idd ++;
//            System.out.println(idd);
//
//        String aa = DateUtil.getDay();
//        ArrayList ay2=new ArrayList();//创建这个类实例
//        String AAA = "{\"code\":\"1000\",\"contentlist\":{\"last_time_score\":\"140\",\"update_tm\":\"2018-12-24 14:25:49\",\"upd_score\":\"300\",\"score\":\"440\",\"userid\":\"00000\"}}";
//        JSONObject JSON = JSONObject.fromObject(AAA);
//        System.out.println(AAA + "");
//        if(AAA.contains("\\\""))
//        {
//            System.out.println("牛了");
//        }
//        System.out.println(AAA.replaceAll("\\\"","\""));
//        if ("1000".equals(JSON.getString("code"))) {
//            System.out.println("没毛病");
//        }

//        String qweqweqwe ="1"; String erwewerw ="-22";
//        int ccc = Integer.valueOf(qweqweqwe) + Integer.valueOf(erwewerw);
//                System.out.print(ccc);
                /*00001————> wt5YqDq06SM=
                00002————>sjQJ/SMWFT8=
                00003————>shzKJAmLy4M=
                00004————>letGAYQk8pQ=
                00005————>P1VBdcMXXis=
                00006————>UuUF2xLFBbk=
                00007————>ViaNFomId2Y=
                00008————>inn6CmPV5sA=
                00009————>JhYRv87WZJQ=
                00010————>2ek4OsdvxrM=
                00011————>26nhwHLJRLk=
                00012————>HjBNpR+i8k8=
                00013————>yOzkQprH7To=*/

              /*00000————> KKK/TZbJP2Y=
                10000————>5O4blFHH5T4=
                20000————>RxTSbaEGyFs=
                30000————>xOooIe4nuuM=
                40000————>rFBThg9ZD5Y=
                50000————>Z/oZ8sVGrDM=
                60000————>CJekBFwZOtw=
                70000————>XxwBGcAsa/4=
                80000————>bRfYlXo9krA=*/
//        String[] qryType={"12121","12121","12121",
//                "123123411423","12312341423"};
////        System.out.println(Arrays.asList(qryType));//先将数组转换为List
//        System.out.println(Arrays.asList(qryType).contains(aaa));
//        Set<String> set = new HashSet<>(Arrays.asList(str));
//        System.out.println(set.contains(s));
    }


}
