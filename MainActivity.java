package com.porpo4785.rockpaperscissors;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // handAnimImageView(가위, 바위, 보가 돌아가는 이미지)선언
    // setHandImageView(컴퓨터가 결정한 손 이미지) 선언
    ImageView handAnimImageView;
    ImageView setHandImageView;

    // 가위, 바위, 보, 리플레이 버튼 선언
    ImageButton gaweButton;
    ImageButton baweButton;
    ImageButton boButton;
    ImageButton replayButton;

    // animationDrawable(배경) 선언
    AnimationDrawable animationDrawable;

    // textToSpeech(음성 출력) 선언
    TextToSpeech textToSpeech;

    TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int i) { // 음성 출력 함수
            if(i != TextToSpeech.ERROR){
                textToSpeech.setLanguage(Locale.KOREAN); // 한국어 음성 출력
                textToSpeech.setPitch(1.0f); // 음성 톤 설정
                textToSpeech.setSpeechRate(1.0f); // 음성 속도 설정
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 애니메이션을 호스팅 할 ImageView 로드
        // 배경을 AnimationDrawable XML 리소스로 설정
        setContentView(R.layout.activity_main);
        handAnimImageView = findViewById(R.id.hand_anim_image_view);
        setHandImageView = findViewById(R.id.set_hand_image_view);


        // 가위, 바위, 보, 리플레이 버튼 불러오기
        gaweButton = findViewById(R.id.gawe_button);
        baweButton = findViewById(R.id.bawe_button);
        boButton = findViewById(R.id.bo_button);
        replayButton = findViewById(R.id.replay_button);

        // AnimationDrawable 객체로 컴파일 된 배경 얻기
        animationDrawable = (AnimationDrawable) handAnimImageView.getDrawable();

        textToSpeech = new TextToSpeech(getApplicationContext(), onInitListener);

    }

    public void button_click(View view) { // 총 4가지 버튼 생성
        switch(view.getId()){
            // 플레이어가 replay 버튼을 누르는 경우
            case R.id.replay_button:
                // replay 버튼을 누르면 setHandImageView를 없엠
                setHandImageView.setVisibility(View.GONE);
                // 대신 handAnimImageView 출력
                handAnimImageView.setVisibility(View.VISIBLE);
                // 게임 시작
                animationDrawable.start();
                // 가위 바위 보 음성 출력
                voicePlay("가위 바위 보");
                // 게임 진행 중 replay 버튼 클릭 금지
                replayButton.setEnabled(false);
                gaweButton.setEnabled(true);
                baweButton.setEnabled(true);
                boButton.setEnabled(true);
                break;
            case R.id.gawe_button:
            case R.id.bawe_button:
            case R.id.bo_button:
                // 게임이 끝나면 가위, 바위, 보 버튼 클릭 금지
                replayButton.setEnabled(true);
                gaweButton.setEnabled(false);
                baweButton.setEnabled(false);
                boButton.setEnabled(false);
                // 게임 끝
                animationDrawable.stop();
                // 게임이 끝나면 정지된 이미지만 출력
                handAnimImageView.setVisibility(View.GONE);
                setHandImageView.setVisibility(View.VISIBLE);
                // 1~3 랜덤 난수 생성
                int getComHand = new Random().nextInt(3) + 1;
                switch(getComHand){
                    // 3가지 케이스별 가위바위보 게임 진행 시 승패 결정
                    case 1: // 컴퓨터가 가위를 내는 경우
                        //화면에 가위 이미지 표시
                        setHandImageView.setImageResource(R.drawable.com_gawe);
                        if(view.getId() == R.id.gawe_button){
                            // 비긴 경우의 음성 출력
                            voicePlay("비겼습니다. 다시 시작 하세요");
                        }else if(view.getId() == R.id.bawe_button){
                            // 플레이어가 이긴 경우의 음성 출력
                            voicePlay("당신이 이겼습니다.");
                        }else{
                            // 플레이어가 진 경우의 음성 출력
                            voicePlay("제가 이겼습니다");
                        }
                        break;
                    case 2: // 컴퓨터가 바위를 내는 경우
                        //화면에 바위 이미지 표시
                        setHandImageView.setImageResource(R.drawable.com_bawe);
                        if(view.getId() == R.id.gawe_button){
                            // 플레이어가 진 경우의 음성 출력
                            voicePlay("제가 이겼습니다");
                        }else if(view.getId() == R.id.bawe_button){
                            // 비긴 경우의 음성 출력
                            voicePlay("비겼습니다. 다시 시작 하세요");
                        }else{
                            // 플레이어가 이긴 경우의 음성 출력
                            voicePlay("당신이 이겼습니다.");
                        }
                        break;
                    case 3: // 컴퓨터가 보를 내는 경우
                        //화면에 보 이미지 표시
                        setHandImageView.setImageResource(R.drawable.com_bo);
                        if(view.getId() == R.id.gawe_button){
                            // 플레이어가 이긴 경우의 음성 출력
                            voicePlay("당신이 이겼습니다.");
                        }else if(view.getId() == R.id.bawe_button){
                            // 플레이어가 진 경우의 음성 출력
                            voicePlay("제가 이겼습니다");
                        }else{
                            // 비긴 경우의 음성 출력
                            voicePlay("비겼습니다. 다시 시작 하세요");
                        }
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        textToSpeech.shutdown();
    }
    public void voicePlay(String voiceText){ // 소리 함수 임의로 생성
        textToSpeech.speak(voiceText,TextToSpeech.QUEUE_FLUSH,null, null);
    }

}