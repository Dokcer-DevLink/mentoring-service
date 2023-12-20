package com.goorm.devlink.mentoringservice;

import com.google.gson.*;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


public class ClovaSpeechClient {

        // Clova Speech secret key
        private static final String SECRET = "d6eb83a6eecb4d81b6c7148868d3f3d6";
        // Clova Speech invoke URL
        private static final String INVOKE_URL = "https://clovaspeech-gw.ncloud.com/external/v1/6563/4e43217aedb02c1859629c68b99d067ce6540bef8ece960f32ce893a34534340";

        private CloseableHttpClient httpClient = HttpClients.createDefault();
        private Gson gson = new Gson();

        private static final Header[] HEADERS = new Header[] {
                new BasicHeader("Accept", "application/json"),
                new BasicHeader("X-CLOVASPEECH-API-KEY", SECRET),
        };

        public static class Boosting {
            private String words;

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }
        }

        // 화자 구분
        public static class Diarization {
            private Boolean enable = Boolean.FALSE;
            private Integer speakerCountMin;
            private Integer speakerCountMax;

            public Boolean getEnable() {
                return enable;
            }

            public void setEnable(Boolean enable) {
                this.enable = enable;
            }

            public Integer getSpeakerCountMin() {
                return speakerCountMin;
            }

            public void setSpeakerCountMin(Integer speakerCountMin) {
                this.speakerCountMin = speakerCountMin;
            }

            public Integer getSpeakerCountMax() {
                return speakerCountMax;
            }

            public void setSpeakerCountMax(Integer speakerCountMax) {
                this.speakerCountMax = speakerCountMax;
            }
        }

        public static class NestRequestEntity {
            private String language = "ko-KR";
            //completion optional, sync/async
            private String completion = "sync";
            //optional, used to receive the analyzed results
            private String callback;
            //optional, any data
            private Map<String, Object> userdata;
            private Boolean wordAlignment = Boolean.FALSE;
            private Boolean fullText = Boolean.TRUE;
            //boosting object array
            private List<Boosting> boostings;
            //comma separated words
            private String forbiddens;
            private Diarization diarization;

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            public String getCompletion() {
                return completion;
            }

            public void setCompletion(String completion) {
                this.completion = completion;
            }

            public String getCallback() {
                return callback;
            }

            public Boolean getWordAlignment() {
                return wordAlignment;
            }

            public void setWordAlignment(Boolean wordAlignment) {
                this.wordAlignment = wordAlignment;
            }

            public Boolean getFullText() {
                return fullText;
            }

            public void setFullText(Boolean fullText) {
                this.fullText = fullText;
            }

            public void setCallback(String callback) {
                this.callback = callback;
            }

            public Map<String, Object> getUserdata() {
                return userdata;
            }

            public void setUserdata(Map<String, Object> userdata) {
                this.userdata = userdata;
            }

            public String getForbiddens() {
                return forbiddens;
            }

            public void setForbiddens(String forbiddens) {
                this.forbiddens = forbiddens;
            }

            public List<Boosting> getBoostings() {
                return boostings;
            }

            public void setBoostings(List<Boosting> boostings) {
                this.boostings = boostings;
            }

            public Diarization getDiarization() {
                return diarization;
            }

            public void setDiarization(Diarization diarization) {
                this.diarization = diarization;
            }
        }

        public String upload(File file, NestRequestEntity nestRequestEntity) {
            HttpPost httpPost = new HttpPost(INVOKE_URL + "/recognizer/upload");
            httpPost.setHeaders(HEADERS);
            HttpEntity httpEntity = MultipartEntityBuilder.create()
                    .addTextBody("params", gson.toJson(nestRequestEntity), ContentType.APPLICATION_JSON)
                    .addBinaryBody("media", file, ContentType.MULTIPART_FORM_DATA, file.getName())
                    .build();
            httpPost.setEntity(httpEntity);
            return execute(httpPost);
        }

        private String execute(HttpPost httpPost) {
            try (final CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                final HttpEntity entity = httpResponse.getEntity();
                return EntityUtils.toString(entity, StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static void main(String[] args) throws IOException {
            final ClovaSpeechClient clovaSpeechClient = new ClovaSpeechClient();
            NestRequestEntity requestEntity = new NestRequestEntity();
            final String result = clovaSpeechClient.upload(new File("/Users/kangmingu/Desktop/devlink_test_3min.mp3"), requestEntity);
            //final String result = clovaSpeechClient.url("file URL", requestEntity); // 외부 버킷에 있는 파일 인식용
            //final String result = clovaSpeechClient.objectStorage("Object Storage key", requestEntity); // 네이버 클라우드 Object Storage에 존재하는 파일 인식용

            //final String result = "{\"result\":\"COMPLETED\",\"message\":\"Succeeded\",\"token\":\"6af8ddcfb73847609d6ee7f374aaacc6\",\"version\":\"ncp_v2_v2.2.3-ncp-c164738-20231016_231023-b281d5fc_v4.2.7_ko_general_torch113_20230727_\",\"params\":{\"service\":\"ncp\",\"domain\":\"general\",\"lang\":\"ko\",\"completion\":\"sync\",\"diarization\":{\"enable\":true,\"speakerCountMin\":-1,\"speakerCountMax\":-1},\"sed\":{\"enable\":false},\"boostings\":[],\"forbiddens\":\"\",\"wordAlignment\":false,\"fullText\":true,\"noiseFiltering\":true,\"resultToObs\":false,\"priority\":0,\"userdata\":{\"_ncp_DomainCode\":\"devlink2-prd\",\"_ncp_DomainId\":6563,\"_ncp_TaskId\":17116618,\"_ncp_TraceId\":\"4c2eff48ee2a467ab92912dbebd2a2a1\"}},\"progress\":100,\"keywords\":{},\"segments\":[{\"start\":0,\"end\":8699,\"text\":\"말씀드렸지만 추정이잖아요. 추정 이게 그죠? 그러니까 지금 성희 님께서 막 고민을 하셔서 5일 정도 걸릴 것 같아요라고 하신 거잖아요. 그죠?\",\"confidence\":0.9471502,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"말씀드렸지만 추정이잖아요. 추정 이게 그죠? 그러니까 지금 성희 님께서 막 고민을 하셔서 5일 정도 걸릴 것 같아요라고 하신 거잖아요. 그죠?\"},{\"start\":8699,\"end\":16645,\"text\":\"근데 진짜 5일 걸릴 거라는 보장이 없는 것들일 거예요. 그러니까 예를 들어서 구축하기 시작했더니 모니터링 5일 만에 구축\",\"confidence\":0.9125181,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"근데 진짜 5일 걸릴 거라는 보장이 없는 것들일 거예요. 그러니까 예를 들어서 구축하기 시작했더니 모니터링 5일 만에 구축\"},{\"start\":16645,\"end\":25275,\"text\":\"쉽지 않을걸 그러니까 뭘로 볼 건데부터 시작을 해서\",\"confidence\":0.9486628,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"쉽지 않을걸 그러니까 뭘로 볼 건데부터 시작을 해서\"},{\"start\":25275,\"end\":30355,\"text\":\"예를 들어서 프로메테우스 써보셨어요 프로메테우스 써보셨어요\",\"confidence\":0.8439144,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"예를 들어서 프로메테우스 써보셨어요 프로메테우스 써보셨어요\"},{\"start\":30355,\"end\":33530,\"text\":\"이론 실습만 해봤어요.\",\"confidence\":0.8802012,\"diarization\":{\"label\":\"2\"},\"speaker\":{\"label\":\"2\",\"name\":\"B\",\"edited\":false},\"words\":[],\"textEdited\":\"이론 실습만 해봤어요.\"},{\"start\":33530,\"end\":42644,\"text\":\"그러니까 보니까 만들려고 하는 게 모니터링만 있는 게 아니고 있던 로깅도 있고 알람 받는 것도 있잖아요. 그죠?\",\"confidence\":0.86160105,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"그러니까 보니까 만들려고 하는 게 모니터링만 있는 게 아니고 있던 로깅도 있고 알람 받는 것도 있잖아요. 그죠?\"},{\"start\":42644,\"end\":56690,\"text\":\"알락 받는 것도 있잖아요. 근데 제가 이거 보면서 무슨 생각했냐면 저는 매트 매트릭도 보셔야 될 거고 로깅도 보셔야 되고 알락도 받으셔야 될 건데 생각해 보시면 매트릭 어디서 받을 건데 그러면\",\"confidence\":0.8318544,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"알락 받는 것도 있잖아요. 근데 제가 이거 보면서 무슨 생각했냐면 저는 매트 매트릭도 보셔야 될 거고 로깅도 보셔야 되고 알락도 받으셔야 될 건데 생각해 보시면 매트릭 어디서 받을 건데 그러면\"},{\"start\":56690,\"end\":61065,\"text\":\"일반적으로 쿠버네티스에서는 플루언트 d 많이 씁니다. 애플리케이션 로그 받는 거\",\"confidence\":0.81476736,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"일반적으로 쿠버네티스에서는 플루언트 d 많이 씁니다. 애플리케이션 로그 받는 거\"},{\"start\":61065,\"end\":71870,\"text\":\"그럼 매트릭은 어떻게 받을 건데라고 했을 때 어떻게 받을 건데요? 프로메테우스로 그러면 그걸 애플리케이션 매트릭 받으실 건데 인프라 매트릭션 어떻게 받으실 거예요?\",\"confidence\":0.8673196,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"그럼 매트릭은 어떻게 받을 건데라고 했을 때 어떻게 받을 건데요? 프로메테우스로 그러면 그걸 애플리케이션 매트릭 받으실 건데 인프라 매트릭션 어떻게 받으실 거예요?\"},{\"start\":72190,\"end\":83130,\"text\":\"인프라 매트릭은 클라우드 워치로 받아야 됩니다. 그렇죠? 그리고 로깅 어떻게 받을 거야 애플리케이션 로깅은 플런트 d로 한다 칩시다. 인프라 로깅은 어떻게 받을 거예요?\",\"confidence\":0.91844183,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"인프라 매트릭은 클라우드 워치로 받아야 됩니다. 그렇죠? 그리고 로깅 어떻게 받을 거야 애플리케이션 로깅은 플런트 d로 한다 칩시다. 인프라 로깅은 어떻게 받을 거예요?\"},{\"start\":85260,\"end\":88660,\"text\":\"vpc 플로우 로그 봐야 됩니다.\",\"confidence\":0.9736776,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"vpc 플로우 로그 봐야 됩니다.\"},{\"start\":88660,\"end\":97365,\"text\":\"얼른 만들 거예요 얼른 어떻게 만들 거예요? 그래 하나로 얼룩 걸 거예요 아니면 클라우드 워치로 sns 연결해서 람다 걸어서 슬랙 받을 거예요 아니면 이메일 받을 거예요\",\"confidence\":0.88778985,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"얼른 만들 거예요 얼른 어떻게 만들 거예요? 그래 하나로 얼룩 걸 거예요 아니면 클라우드 워치로 sns 연결해서 람다 걸어서 슬랙 받을 거예요 아니면 이메일 받을 거예요\"},{\"start\":97365,\"end\":99755,\"text\":\"람다 사용할 예정이었습\",\"confidence\":0.78892684,\"diarization\":{\"label\":\"2\"},\"speaker\":{\"label\":\"2\",\"name\":\"B\",\"edited\":false},\"words\":[],\"textEdited\":\"람다 사용할 예정이었습\"},{\"start\":99755,\"end\":106840,\"text\":\"그렇죠 람다 사용할 거면 그 시작점을 그라 하나로 걸 거예요. 그러니까 스루풋을 만들어준 애를 그랍하나로 걸 거예요 클라우드 워치로 걸 거예요\",\"confidence\":0.8125857,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"그렇죠 람다 사용할 거면 그 시작점을 그라 하나로 걸 거예요. 그러니까 스루풋을 만들어준 애를 그랍하나로 걸 거예요 클라우드 워치로 걸 거예요\"},{\"start\":106840,\"end\":114500,\"text\":\"이런 식으로 고민해야 될 게 엄청 많을 겁니다. 이게 5일 만에 끝날 리가 없어요. 한 이 정도 걸릴 것 같아요. 내가 보기에는\",\"confidence\":0.9648319,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"이런 식으로 고민해야 될 게 엄청 많을 겁니다. 이게 5일 만에 끝날 리가 없어요. 한 이 정도 걸릴 것 같아요. 내가 보기에는\"},{\"start\":114500,\"end\":118739,\"text\":\"이런 식으로 날짜 다시 잡았을 가능성이 큽니다. 한번\",\"confidence\":0.9722664,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"이런 식으로 날짜 다시 잡았을 가능성이 큽니다. 한번\"},{\"start\":118739,\"end\":128840,\"text\":\"잡아보시는 게 좋을 것 같고 그리고 기간이 만약에 일정 조정을 해봤는데도 그래도 기간이 남는다. 이걸 다시 해보셨는데 그래도 기간이 남는다면 피처가 부족한 겁니다.\",\"confidence\":0.9787261,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"잡아보시는 게 좋을 것 같고 그리고 기간이 만약에 일정 조정을 해봤는데도 그래도 기간이 남는다. 이걸 다시 해보셨는데 그래도 기간이 남는다면 피처가 부족한 겁니다.\"},{\"start\":128840,\"end\":130970,\"text\":\"난이도가 너무 쉬운 거예요.\",\"confidence\":0.9986772,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"난이도가 너무 쉬운 거예요.\"},{\"start\":132610,\"end\":143640,\"text\":\"너무 쉬운 걸 하고 있는 거예요. 그러니까 지금 이게 빨리 끝난다고 하는 게 말씀하신 대로 이미 제작 중인 거에 참여하는 것 때문일 수도 있어요.\",\"confidence\":0.98583204,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"너무 쉬운 걸 하고 있는 거예요. 그러니까 지금 이게 빨리 끝난다고 하는 게 말씀하신 대로 이미 제작 중인 거에 참여하는 것 때문일 수도 있어요.\"},{\"start\":144350,\"end\":157045,\"text\":\"근데 그렇다면 그만큼 더 챌린지 되는 게 있어야 돼요. 왜냐면 다른 2조 분들은 지금 제로부터 시작하는데 우린 제로로 시작하는 게 아니잖아요 그렇죠? 그러면 제로로 시작하는 게 아닌 만큼 뭔가 챌린지 되는 게 있어야 되는 거예요.\",\"confidence\":0.8817848,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"근데 그렇다면 그만큼 더 챌린지 되는 게 있어야 돼요. 왜냐면 다른 2조 분들은 지금 제로부터 시작하는데 우린 제로로 시작하는 게 아니잖아요 그렇죠? 그러면 제로로 시작하는 게 아닌 만큼 뭔가 챌린지 되는 게 있어야 되는 거예요.\"},{\"start\":157045,\"end\":165290,\"text\":\"그렇습니다. 그래서 그리고 여러분들이 말씀하신 대로 포트폴리오에 넣으려면 생신된 게 좀 있어야 됩니다.\",\"confidence\":0.8963967,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"그렇습니다. 그래서 그리고 여러분들이 말씀하신 대로 포트폴리오에 넣으려면 생신된 게 좀 있어야 됩니다.\"},{\"start\":165290,\"end\":177140,\"text\":\"그러니까 그런 걸 좀 보시면 좋을 것 같고요. 그래서 일단은 제가 말씀드릴 내용은 이 정도였던 것 같아요 이쪽은. 그래서 지금 고려하니까 지금 제가 이제 했던 거 기억하세요. 하는 법.\",\"confidence\":0.9245908,\"diarization\":{\"label\":\"1\"},\"speaker\":{\"label\":\"1\",\"name\":\"A\",\"edited\":false},\"words\":[],\"textEdited\":\"그러니까 그런 걸 좀 보시면 좋을 것 같고요. 그래서 일단은 제가 말씀드릴 내용은 이 정도였던 것 같아요 이쪽은. 그래서 지금 고려하니까 지금 제가 이제 했던 거 기억하세요. 하는 법.\"},{\"start\":177140,\"end\":178680,\"text\":\"네.\",\"confidence\":0.68603426,\"diarization\":{\"label\":\"2\"},\"speaker\":{\"label\":\"2\",\"name\":\"B\",\"edited\":false},\"words\":[],\"textEdited\":\"네.\"}],\"text\":\"말씀드렸지만 추정이잖아요. 추정 이게 그죠? 그러니까 지금 성희 님께서 막 고민을 하셔서 5일 정도 걸릴 것 같아요라고 하신 거잖아요. 그죠? 근데 진짜 5일 걸릴 거라는 보장이 없는 것들일 거예요. 그러니까 예를 들어서 구축하기 시작했더니 모니터링 5일 만에 구축 쉽지 않을걸 그러니까 뭘로 볼 건데부터 시작을 해서 예를 들어서 프로메테우스 써보셨어요 프로메테우스 써보셨어요 이론 실습만 해봤어요. 그러니까 보니까 만들려고 하는 게 모니터링만 있는 게 아니고 있던 로깅도 있고 알람 받는 것도 있잖아요. 그죠? 알락 받는 것도 있잖아요. 근데 제가 이거 보면서 무슨 생각했냐면 저는 매트 매트릭도 보셔야 될 거고 로깅도 보셔야 되고 알락도 받으셔야 될 건데 생각해 보시면 매트릭 어디서 받을 건데 그러면 일반적으로 쿠버네티스에서는 플루언트 d 많이 씁니다. 애플리케이션 로그 받는 거 그럼 매트릭은 어떻게 받을 건데라고 했을 때 어떻게 받을 건데요? 프로메테우스로 그러면 그걸 애플리케이션 매트릭 받으실 건데 인프라 매트릭션 어떻게 받으실 거예요? 인프라 매트릭은 클라우드 워치로 받아야 됩니다. 그렇죠? 그리고 로깅 어떻게 받을 거야 애플리케이션 로깅은 플런트 d로 한다 칩시다. 인프라 로깅은 어떻게 받을 거예요? vpc 플로우 로그 봐야 됩니다. 얼른 만들 거예요 얼른 어떻게 만들 거예요? 그래 하나로 얼룩 걸 거예요 아니면 클라우드 워치로 sns 연결해서 람다 걸어서 슬랙 받을 거예요 아니면 이메일 받을 거예요 람다 사용할 예정이었습 그렇죠 람다 사용할 거면 그 시작점을 그라 하나로 걸 거예요. 그러니까 스루풋을 만들어준 애를 그랍하나로 걸 거예요 클라우드 워치로 걸 거예요 이런 식으로 고민해야 될 게 엄청 많을 겁니다. 이게 5일 만에 끝날 리가 없어요. 한 이 정도 걸릴 것 같아요. 내가 보기에는 이런 식으로 날짜 다시 잡았을 가능성이 큽니다. 한번 잡아보시는 게 좋을 것 같고 그리고 기간이 만약에 일정 조정을 해봤는데도 그래도 기간이 남는다. 이걸 다시 해보셨는데 그래도 기간이 남는다면 피처가 부족한 겁니다. 난이도가 너무 쉬운 거예요. 너무 쉬운 걸 하고 있는 거예요. 그러니까 지금 이게 빨리 끝난다고 하는 게 말씀하신 대로 이미 제작 중인 거에 참여하는 것 때문일 수도 있어요. 근데 그렇다면 그만큼 더 챌린지 되는 게 있어야 돼요. 왜냐면 다른 2조 분들은 지금 제로부터 시작하는데 우린 제로로 시작하는 게 아니잖아요 그렇죠? 그러면 제로로 시작하는 게 아닌 만큼 뭔가 챌린지 되는 게 있어야 되는 거예요. 그렇습니다. 그래서 그리고 여러분들이 말씀하신 대로 포트폴리오에 넣으려면 생신된 게 좀 있어야 됩니다. 그러니까 그런 걸 좀 보시면 좋을 것 같고요. 그래서 일단은 제가 말씀드릴 내용은 이 정도였던 것 같아요 이쪽은. 그래서 지금 고려하니까 지금 제가 이제 했던 거 기억하세요. 하는 법. 네.\",\"confidence\":0.90134245,\"speakers\":[{\"label\":\"1\",\"name\":\"A\",\"edited\":false},{\"label\":\"2\",\"name\":\"B\",\"edited\":false}],\"events\":[],\"eventTypes\":[]}" ;
            InputStream inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = br.readLine();

            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(line);
            JsonArray jsonArray = (JsonArray)jsonObject.get("segments");

            StringBuilder sb = new StringBuilder();
            String curName = "";
            String curStr = "";

            for (JsonElement jsonElement : jsonArray) {
                JsonObject obj = jsonElement.getAsJsonObject();
                String name = obj.get("speaker").getAsJsonObject().get("name").toString().replaceAll("\"","");
                String str = obj.get("text").toString().replaceAll("\"","");

//                System.out.println("curName: " + curName);
//                System.out.println("name: " + name);
//                System.out.println("curStr: " + curStr);
//                System.out.println("str: " + str);

                if(curName.equals(name)){
                    curStr += " " + str;
                }else{
                    if(curName.isEmpty()){
                        curName = name;
                        curStr = str;
                    }
                    else{
                        sb.append("개발자").append(curName).append(":").append(" ").append(curStr).append("\n");
                        curName = name;
                        curStr = str;
                    }
                }
            }
            sb.append("개발자".concat(curName).concat(":").concat(" ").concat(curStr));
            System.out.println(sb.toString());

        }

}
