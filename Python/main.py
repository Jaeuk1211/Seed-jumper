import math
import cv2
import numpy as np
from time import time
import mediapipe as mp
import matplotlib as plt
import time
import socket
import pygame

# Initializing mediapipe pose class.
mp_pose = mp.solutions.pose

# Setting up the Pose function.
pose = mp_pose.Pose(static_image_mode=True, min_detection_confidence=0.3, model_complexity=2)

# Initializing mediapipe drawing class, useful for annotation.
mp_drawing = mp.solutions.drawing_utils

# Initializing pygame module for audio feedback
pygame.init()
sound = pygame.mixer.music

def detectPose(image, pose, display=True):
    '''
    This function performs pose detection on an image.
    Args:
        image: The input image with a prominent person whose pose landmarks needs to be detected.
        pose: The pose setup function required to perform the pose detection.
        display: A boolean value that is if set to true the function displays the original input image, the resultant image,
                 and the pose landmarks in 3D plot and returns nothing.
    Returns:
        output_image: The input image with the detected pose landmarks drawn.
        landmarks: A list of detected landmarks converted into their original scale.
    '''

    # Create a copy of the input image.
    output_image = image.copy()

    # Convert the image from BGR into RGB format.
    imageRGB = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)

    # Perform the Pose Detection.
    results = pose.process(imageRGB)

    # Retrieve the height and width of the input image.
    height, width, _ = image.shape

    # Initialize a list to store the detected landmarks.
    landmarks = []

    # Check if any landmarks are detected.
    if results.pose_landmarks:

        # Draw Pose landmarks on the output image.
        mp_drawing.draw_landmarks(image=output_image, landmark_list=results.pose_landmarks,
                                  connections=mp_pose.POSE_CONNECTIONS)

        # Iterate over the detected landmarks.
        for landmark in results.pose_landmarks.landmark:
            # Append the landmark into the list.
            landmarks.append((int(landmark.x * width), int(landmark.y * height),
                              (landmark.z * width)))

    # Return the output image and the found landmarks.
    return output_image, landmarks


def calculateAngle(landmark1, landmark2, landmark3):
    '''
    This function calculates angle between three different landmarks.
    Args:
        landmark1: The first landmark containing the x,y and z coordinates.
        landmark2: The second landmark containing the x,y and z coordinates.
        landmark3: The third landmark containing the x,y and z coordinates.
    Returns:
        angle: The calculated angle between the three landmarks.

    '''

    # Get the required landmarks coordinates.
    x1, y1, _ = landmark1
    x2, y2, _ = landmark2
    x3, y3, _ = landmark3

    # Calculate the angle between the three points
    angle = math.degrees(math.atan2(y3 - y2, x3 - x2) - math.atan2(y1 - y2, x1 - x2))

    # Check if the angle is less than zero.
    if angle < 0:
        # Add 360 to the found angle.
        angle += 360

    # Return the calculated angle.
    return angle


def classifyPose(landmarks, output_image, display=False):
    '''
    This function classifies yoga poses depending upon the angles of various body joints.
    Args:
        landmarks: A list of detected landmarks of the person whose pose needs to be classified.
        output_image: A image of the person with the detected pose landmarks drawn.
        display: A boolean value that is if set to true the function displays the resultant image with the pose label
        written on it and returns nothing.
    Returns:
        output_image: The image with the detected pose landmarks drawn and pose label written.
        label: The classified pose label of the person in the output_image.

    '''

    # Initialize the label of the pose. It is not known at this stage.
    label = 'Not jumping'

    # Specify the color (Red) with which the label will be written on the image.
    color = (0, 0, 255)

    # Calculate the required angles.
    # ----------------------------------------------------------------------------------------------------------------

    # Get the angle between the left shoulder, elbow and wrist points.
    left_elbow_angle = calculateAngle(landmarks[mp_pose.PoseLandmark.LEFT_SHOULDER.value],
                                      landmarks[mp_pose.PoseLandmark.LEFT_ELBOW.value],
                                      landmarks[mp_pose.PoseLandmark.LEFT_WRIST.value])

    # Get the angle between the right shoulder, elbow and wrist points.
    right_elbow_angle = calculateAngle(landmarks[mp_pose.PoseLandmark.RIGHT_SHOULDER.value],
                                       landmarks[mp_pose.PoseLandmark.RIGHT_ELBOW.value],
                                       landmarks[mp_pose.PoseLandmark.RIGHT_WRIST.value])

    # Get the angle between the left elbow, shoulder and hip points.
    left_shoulder_angle = calculateAngle(landmarks[mp_pose.PoseLandmark.LEFT_ELBOW.value],
                                         landmarks[mp_pose.PoseLandmark.LEFT_SHOULDER.value],
                                         landmarks[mp_pose.PoseLandmark.LEFT_HIP.value])

    # Get the angle between the right hip, shoulder and elbow points.
    right_shoulder_angle = calculateAngle(landmarks[mp_pose.PoseLandmark.RIGHT_HIP.value],
                                          landmarks[mp_pose.PoseLandmark.RIGHT_SHOULDER.value],
                                          landmarks[mp_pose.PoseLandmark.RIGHT_ELBOW.value])

    # Get the angle between the left hip, knee and ankle points.
    left_knee_angle = calculateAngle(landmarks[mp_pose.PoseLandmark.LEFT_HIP.value],
                                     landmarks[mp_pose.PoseLandmark.LEFT_KNEE.value],
                                     landmarks[mp_pose.PoseLandmark.LEFT_ANKLE.value])

    # Get the angle between the right hip, knee and ankle points
    right_knee_angle = calculateAngle(landmarks[mp_pose.PoseLandmark.RIGHT_HIP.value],
                                      landmarks[mp_pose.PoseLandmark.RIGHT_KNEE.value],
                                      landmarks[mp_pose.PoseLandmark.RIGHT_ANKLE.value])

    # ----------------------------------------------------------------------------------------------------------------

    leftFoot = landmarks[mp_pose.PoseLandmark.LEFT_FOOT_INDEX.value]
    rightFoot = landmarks[mp_pose.PoseLandmark.RIGHT_FOOT_INDEX.value]

    if left_shoulder_angle > 35 and left_shoulder_angle < 55 and right_shoulder_angle > 35 and right_shoulder_angle < 55:
        # Check if one leg is straight.
        if left_knee_angle > 170 and left_knee_angle < 190 and right_knee_angle > 170 and right_knee_angle < 190:
            if 550 < leftFoot[1] and leftFoot[1] < 610 and 550 < rightFoot[1] and rightFoot[1] < 610:
                label = 'Jump!'

                # ----------------------------------------------------------------------------------------------------------------

    # Check if the pose is classified successfully
    if label != 'Not jumping':
        # Update the color (to green) with which the label will be written on the image.
        color = (0, 255, 0)

        # Write the label on the output image.
    cv2.putText(output_image, label, (10, 30), cv2.FONT_HERSHEY_PLAIN, 2, color, 2)

    # Return the output image and the classified label.
    return output_image, label


# Setup Pose function for video.
pose_video = mp_pose.Pose(static_image_mode=False, min_detection_confidence=0.5, model_complexity=1)

# Initialize the VideoCapture object to read from the webcam.
camera_video = cv2.VideoCapture(0)
camera_video.set(3, 1280)
camera_video.set(4, 960)

# Variables to check the jump status and combo (includes time)
percent = 0
combo = 0
combosum = 0
flag = 0
t1 = 0
t2 = 0
timeflag = 0
#starttime = 0
#endtime = -1
#exercisetime = 0
socketflag = 0
goal = 0

HOST = ""
PORT = 7777
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print('socket created')
s.bind((HOST, PORT))
print('Socket bind complete')
s.listen(1)
print('socket now listening...')
count = 0

# Iterate until the webcam is accessed successfully.
while camera_video.isOpened():
    conn, addr = s.accept()
    print('Connected by?!', addr)  # 연결주소 print
    data = conn.recv(1024)  # 안드로이드에서 "refresh" 전송
    result = data.decode("utf-8")
    print('rcv :', result, "len: ", len(data))  # 전송 받은값 디코딩

    if socketflag == 0:
        lv = int(result[:1]) # 장르 저장할 변수
        genre = int(result[-1:]) # 레벨 저장할 변수

        if genre == 1:   
            sounda = pygame.mixer.Sound('C:/Users/LG/PycharmProjects/pythonProject/music/'+ 'pop' + str(lv)+ '.mp3')
        elif genre == 2:
            sounda = pygame.mixer.Sound('C:/Users/LG/PycharmProjects/pythonProject/music/'+ 'dance' + str(lv)+ '.mp3')
        elif genre == 3:
            sounda = pygame.mixer.Sound('C:/Users/LG/PycharmProjects/pythonProject/music/'+ 'country' + str(lv)+ '.mp3')
        elif genre == 4:
            sounda = pygame.mixer.Sound('C:/Users/LG/PycharmProjects/pythonProject/music/'+ 'rock' + str(lv)+ '.mp3')
        elif genre == 5:
            sounda = pygame.mixer.Sound('C:/Users/LG/PycharmProjects/pythonProject/music/'+ 'hiphop' + str(lv)+ '.mp3')

        sounda.play()
        goal = lv * 100
        socketflag = 1

    # Read a frame.
    ok, frame = camera_video.read()

    # Check if frame is not read properly.
    if not ok:
        # Continue to the next iteration to read the next frame and ignore the empty camera frame.
        continue

    # Flip the frame horizontally for natural (selfie-view) visualization.
    frame = cv2.flip(frame, 1)

    # Get the width and height of the frame
    frame_height, frame_width, _ = frame.shape

    # Resize the frame while keeping the aspect ratio.
    frame = cv2.resize(frame, (int(frame_width * (640 / frame_height)), 640))

    # Perform Pose landmark detection.
    frame, landmarks = detectPose(frame, pose_video, display=False)
    output = str(percent)
    # Check if the landmarks are detected.
    if landmarks:

        # Detect the Jump Pose
        frame, temp = classifyPose(landmarks, frame, display=False)

        # Set the start time
        if timeflag == 0 :
            timeflag = 1
            #starttime = time.time()

        if temp != 'Not jumping':

            # When the Pose Detects 'Jumping', increment +1 to the percent
            # Also, get the time
            percent = percent + 1
            t1 = t2
            t2 = time.time()

            # If within a second, continuous jump occurs, increment +1 to the combo
            if t2 - t1 < 1:
                combo = combo + 1
                cv2.putText(frame, 'Combo!', (520, 500), cv2.FONT_HERSHEY_PLAIN, 2, (255, 0, 0), 2)
                cv2.putText(frame, '{}'.format(combo), (560, 550), cv2.FONT_HERSHEY_PLAIN, 2, (255, 0, 0), 2)
            else:
                combosum = combosum + combo
                combo = 0

    # Display the frame.
    if percent >= goal:
        cv2.putText(frame, 'Clear!!', (1000, 30), cv2.FONT_HERSHEY_PLAIN, 2, (0, 255, 0), 2)
        #endtime = time.time()
        break
    else:
        if percent!= 0 and percent%10 == 0 and soundflag == 0:
            temp = ''
            if percent >= 100 :
                temp = str(percent)[1:]
            else :
                temp = str(percent)
            soundb = pygame.mixer.Sound('Python/numaudio/'+ temp +'.wav')
            soundb.play()
            soundflag = 1
        else :
            if percent%10 != 0 :
                soundflag = 0

        cv2.putText(frame, '{}/{}'.format(percent,goal), (1000, 30), cv2.FONT_HERSHEY_PLAIN, 2, (0, 0, 0), 2)
        output = str(percent) + "#" + str(combo)
        conn.sendall(output.encode())
        #print("percent: ", percent)
        conn.close()


    # The Line for the tip of the foot
    cv2.line(frame, (500, 630), (550, 630), (0, 255, 255), cv2.LINE_AA)
    cv2.line(frame, (600, 630), (650, 630), (0, 255, 255), cv2.LINE_AA)

    cv2.line(frame, (500, 610), (550, 610), (0, 0, 255), cv2.LINE_AA)
    cv2.line(frame, (600, 610), (650, 610), (0, 0, 255), cv2.LINE_AA)
    cv2.imshow('Seed Jumper', frame)

    # Wait until a key is pressed.
    # Retreive the ASCII code of the key pressed
    k = cv2.waitKey(1) & 0xFF

    # Check if 'ESC' is pressed.
    if (k == 27):
        # Break the loop.
        break

# Release the VideoCapture object and close the windows.
"""
if endtime != -1 :
    exercisetime = int(endtime - starttime)
"""
camera_video.release()
cv2.destroyAllWindows()
