import React, { useState } from "react";
import {
  Page,
  Text,
  Image,
  Font,
  View,
  Document,
  StyleSheet,
} from "@react-pdf/renderer";

export default function IntelligenceCreator(props) {
  Font.register({
    family: "nanumGothic",
    src: "https://fonts.gstatic.com/ea/nanumgothic/v5/NanumGothic-Regular.ttf",
  });

  Font.register({
    family: "nanumGothicBold",
    src: "https://fonts.gstatic.com/ea/nanumgothic/v5/NanumGothic-Bold.ttf",
  });

  const styles = StyleSheet.create({
    table: {
      //   border: 1,
    },
    image: {
      marginVertical: 15,
      marginHorizontal: 100,
    },
    body: {
      paddingTop: 35,
      paddingBottom: 65,
      paddingHorizontal: 35,
    },
    title: {
      fontSize: 30,
      textAlign: "left",
      color: "#2d2e30",
      fontFamily: "nanumGothicBold",
      marginBottom: 40,
      marginLeft: 20,
      display: "block",
    },
    text: {
      margin: 12,
      fontSize: 14,
      textAlign: "justify",
      fontFamily: "nanumGothic",
      marginBottom: 30,
      display: "block",
    },
    header: {
      fontSize: 10,
      marginBottom: 20,
      textAlign: "right",
      color: "#81848a",
      fontFamily: "nanumGothic",
      display: "block",
    },
    pageNumber: {
      position: "absolute",
      fontSize: 12,
      bottom: 30,
      left: 0,
      right: 0,
      textAlign: "center",
      color: "grey",
    },
  });
  return (
    <Document>
      <Page style={styles.body}>
        <Text style={styles.header} fixed>
          사이버수사관 모니터링 보조 업무 시스템
        </Text>
        <Text style={styles.title} fixed>
          게시물 모니터링 위험 징후 보고
        </Text>

        <table style={styles.table}>
          <tr>
            <th width="150">
              <Text style={styles.text}>첩보명</Text>
            </th>
            <td width="500">
              <Text style={styles.text}>
                {"학폭 사건의 피해 호소 주장 글로 의심되는 게시물 게재 관련 "}
              </Text>
            </td>
          </tr>
          <tr>
            <th>
              <Text style={styles.text}>첩보 유형</Text>
            </th>
            <td>
              <Text style={styles.text}>{"피해 호소 주장"}</Text>
            </td>
          </tr>
          <tr>
            <th>
              <Text style={styles.text}>관련 법</Text>
            </th>
            <td>
              <Text style={styles.text}>{"정보통신망법"}</Text>
            </td>
          </tr>
          <tr>
            <th>
              <Text style={styles.text}>게시물명</Text>
            </th>
            <td>
              <Text style={styles.text}>
                {"과거 학폭으로 인한 트라우마, 나쁜 선택을 하려는 나"}
              </Text>
            </td>
          </tr>
          <tr>
            <th>
              <Text style={styles.text}>사이트</Text>
            </th>
            <td>
              <Text style={styles.text}>{"네이트 판"}</Text>
            </td>
          </tr>
          <tr>
            <th>
              <Text style={styles.text}>작성자</Text>
            </th>
            <td>
              <Text style={styles.text}>{"oo(닉네임)"}</Text>
            </td>
          </tr>
          <tr>
            <th>
              <Text style={styles.text}>작성일</Text>
            </th>
            <td>
              {" "}
              <Text style={styles.text}>
                {"2021.04.04 09:08 (UTC +09:00, Seoul)"}
              </Text>
            </td>
          </tr>
          <tr>
            <th>
              <Text style={styles.text}>URL</Text>
            </th>
            <td>
              {" "}
              <Text style={styles.text}>
                {"https://pann.nate.com/talk/358871562"}
              </Text>
            </td>
          </tr>
          <tr>
            <th>
              <Text style={styles.text}>첩보 내용</Text>
            </th>
            <td>
              <Text style={styles.text}>
                {
                  "최근 연쇄적으로 발생되고 있는 학교폭력(줄임말 '학폭') 폭로 사건과 관련하여 새롭게 추가된 피해 호소 주장글로, 과거에 받았던 피해와 그 고통에 대해서 호소 하는 내용으로 이루어져 있음."
                }
              </Text>
            </td>
          </tr>
          <tr>
            <th>
              <Text style={styles.text}>수사관 의견</Text>
            </th>
            <td>
              <Text style={styles.text}>
                {
                  "'학폭'사건이 연쇄적으로 발생, 최근 사회적 공분을 일으키고 있는 사건으로 관심도가 상당하기에, 관련하여 지속적으로 관련 키워드 모니터링을 집중 수행하며, 가짜뉴스 유포 등의 의심 징후가 발견될 경우 즉시 내사 진행"
                }
              </Text>
            </td>
          </tr>
          <tr>
            <th>
              <Text style={styles.text}>내사 진행 여부</Text>
            </th>
            <td>
              <Text style={styles.text}>{"보류"}</Text>
            </td>
          </tr>
          <tr>
            <th>
              <Text style={styles.text}>페이지 스냅샷</Text>
            </th>
            <td>
              <Text style={styles.text}>
                {
                  "https://monitor-asissistant-ap-northeast-2a.s3.amazonnaws.com/..."
                }
              </Text>
            </td>
          </tr>

          <Text
            style={styles.pageNumber}
            render={({ pageNumber, totalPages }) =>
              `${pageNumber} / ${totalPages}`
            }
            fixed
          />
        </table>
      </Page>
    </Document>
  );
}
