package com.goorm.devlink.mentoringservice.record.impl;

import com.goorm.devlink.mentoringservice.config.properties.vo.NaverApiConfigVo;
import com.goorm.devlink.mentoringservice.record.NaverClovaApi;
import com.goorm.devlink.mentoringservice.util.HttpConnectionUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

import java.net.HttpURLConnection;


@RequiredArgsConstructor
public class NaverSummary implements NaverClovaApi {

    private final HttpConnectionUtil httpConnectionUtil;
    private final NaverApiConfigVo naverApiConfigVo;

    @Override
    public String sendDataToNaverClova(String content) {
        System.out.println("============ Naver Summary ================");

        HttpURLConnection conn = httpConnectionUtil.getHttpUrlConnection(naverApiConfigVo.getSummary().getUrl(),
                naverApiConfigVo.getSummary().getContentType(),
                naverApiConfigVo.getClientId(),
                naverApiConfigVo.getClientSecret()
        );

        String test = "안녕하세요 강민구라고 합니다 만나서 반갑습니다 현재 녹음 테스트 중입니다";
        String testContent0 = "전문적인 강민구는 하루 평균 10시간을 일한다. 현재는 녹음 테스트 중에 있다.";
        String testContent1 = "간편송금 이용금액이 하루 평균 2000억원을 넘어섰다.";
        String testContent2 = "간편송금 이용금액이 하루 평균 2000억원을 넘어섰다. 한국은행이 17일 발표한 '2019년 상반기중 전자지급서비스 이용 현황'에 따르면 올해 상반기 간편송금서비스 이용금액(일평균)은 지난해 하반기 대비 60.7% 증가한 2005억원으로 집계됐다. 같은 기간 이용건수(일평균)는 34.8% 늘어난 218만건이었다. 간편 송금 시장에는 선불전자지급서비스를 제공하는 전자금융업자와 금융기관 등이 참여하고 있다. 이용금액은 전자금융업자가 하루평균 1879억원, 금융기관이 126억원이었다. 한은은 카카오페이, 토스 등 간편송금 서비스를 제공하는 업체 간 경쟁이 심화되면서 이용규모가 크게 확대됐다고 분석했다. 국회 정무위원회 소속 바른미래당 유의동 의원에 따르면 카카오페이, 토스 등 선불전자지급서비스 제공업체는 지난해 마케팅 비용으로 1000억원 이상을 지출했다. 마케팅 비용 지출규모는 카카오페이가 491억원, 비바리퍼블리카(토스)가 134억원 등 순으로 많았다.";

        String testContent3 = "삼성바이오는 롯데바이오에도 \"귀사 입사 예정자의 영업비밀 무단유출 행위가 적발돼 법적 조치가 진행 중\"이라는 내용증명을 발송한 것으로 전해졌다.\n" +
                "\n" +
                "삼성바이오는 롯데바이오가 사업을 본격화한 지난해부터 인력 빼가기 및 기밀 유출 의혹을 놓고 대립하고 있다.\n" +
                "\n" +
                "삼성바이오는 지난해 자사에서 롯데바이오로 이직한 3명을 상대로 영업비밀 침해 금지 가처분 신청을 냈고, 그해 7월 인천지법에서 일부 인용 결정을 받았다.\n" +
                "\n" +
                "또 같은 해 8~9월 삼성바이오는 롯데바이오로 이직한 직원 4명을 형사 고소했으며, 이 가운데 1명은 올해 3월 인천지검이 부정경쟁방지 및 영업비밀보호에 관한 법률 위반과 업무상 배임 혐의로 불구속기소 했다.";

        JSONObject document = new JSONObject();
        document.put("title","title");
        document.put("content",testContent3);
        JSONObject option = new JSONObject();
        option.put("language","ko");
        option.put("model","general");
        option.put("tone",0);
        option.put("summaryCount",3);

        JSONObject sendJson = new JSONObject();
        sendJson.put("document",document);
        sendJson.put("option",option);

        httpConnectionUtil.sendJsonToServer(conn,sendJson);
        return httpConnectionUtil.receiveResponseFromServer(conn);
    }
}
