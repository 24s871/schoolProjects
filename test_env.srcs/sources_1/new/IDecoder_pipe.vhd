----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 05/07/2023 06:56:36 PM
-- Design Name: 
-- Module Name: IDecoder_pipe - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity IDecoder_pipe is
  Port (clk:in STD_LOGIC;
        Instruction_pipe:in STD_LOGIC_VECTOR(15 downto 0);
        en:in STD_LOGIC;
        WD:in STD_LOGIC_VECTOR(15 downto 0);
        RegWrite:in STD_LOGIC;
        ExtOp:in STD_LOGIC;
        RD1:out STD_LOGIC_VECTOR(15 downto 0);
        RD2:out STD_LOGIC_VECTOR(15 downto 0);
        Ext_Imm:out STD_LOGIC_VECTOR(15 downto 0);
        func:out STD_LOGIC_VECTOR(2 downto 0);
        sa:out STD_LOGIC;
        rd:out std_logic_vector(2 downto 0);
        rt:out std_logic_vector(2 downto 0));
end IDecoder_pipe;

architecture Behavioral of IDecoder_pipe is
component REGFILE is
   Port ( 
   RA1:in STD_LOGIC_VECTOR(2 downto 0);
   RA2:in STD_LOGIC_VECTOR(2 downto 0);
   clk:in STD_LOGIC;
   WA:in STD_LOGIC_VECTOR(2 downto 0);
   WD:in STD_LOGIC_VECTOR(15 downto 0);
   RD1:out STD_LOGIC_VECTOR(15 downto 0);
   RD2:out STD_LOGIC_VECTOR(15 downto 0);
   WEN:in STD_LOGIC); 
end component;
signal write_addres:STD_LOGIC_VECTOR(2 downto 0);
signal EXT:STD_LOGIC_VECTOR(15 downto 7);
begin
REG:REGFILE port map(RA1=>Instruction_pipe(12 downto 10),RA2=>Instruction_pipe(9 downto 7),WA=>write_addres,clk=>clk,WD=>WD,RD1=>RD1,RD2=>RD2,WEN=>RegWrite);
func<=Instruction_pipe(2 downto 0);
sa<=Instruction_pipe(3);
extindere:process(ExtOp)
begin
if(ExtOp='0') then
EXT<=(others=>'0');
else EXT<=(others=>Instruction_pipe(6));
end if;
Ext_Imm<=EXT&Instruction_pipe(6 downto 0);
end process;
--rd<=Instruction_pipe(12 downto 10);
--rt<=Instruction_pipe(9 downto 7);
end Behavioral;
